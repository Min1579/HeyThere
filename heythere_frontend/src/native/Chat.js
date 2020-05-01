import React from 'react';
import SockJsClient from "react-stomp";
import UsernameGenerator from "username-generator";
import Fetch from "json-fetch";
import { TalkBox } from "react-talk";
import randomstring from"randomstring";

class Chat extends React.Component {

    constructor(props) {
        super(props);
        // randomUserId is used to emulate a unique user id for this demo usage
        this.randomUserName = UsernameGenerator.generateUsername("-");
        this.randomUserId = randomstring.generate();
        this.sendURL = "http://localhost:8080/message";
        this.state = {
            clientConnected: false,
            messages: []
        };

    }

    onMessageReceive = (msg, topic) => {
        //alert(JSON.stringify(msg) + " @ " +  JSON.stringify(this.state.messages)+" @ " + JSON.stringify(topic));
        this.setState(prevState => ({
            messages: [...prevState.messages, msg]
        }));
    }

    sendMessage = (msg, selfMsg) => {
        try {
            const send_message = {
                "user": selfMsg.author,
                "message": selfMsg.message
            }
            this.clientRef.sendMessage("http://localhost:8080/app/message", JSON.stringify(send_message));
            return true;
        } catch (e) {
            return false;
        }
    }


    render() {
        const wsSourceUrl = "http://localhost:8080/chatting";
        return (
            <div>
                <TalkBox topic="/topic/public" currentUserId={ this.randomUserId }
                         currentUser={ this.randomUserName } messages={ this.state.messages }
                         onSendMessage={ this.sendMessage } connected={ this.state.clientConnected }/>

                <SockJsClient url={ wsSourceUrl } topics={["/topic/public"]}
                              onMessage={ this.onMessageReceive } ref={ (client) => { this.clientRef = client }}
                              onConnect={ () => {this.setState({ clientConnected: true }) } }
                              onDisconnect={ () => { this.setState({ clientConnected: false }) } }
                              debug={ false } style={[{width:'100%', height:'100%'}]}/>
            </div>
        );
    }
}

export default Chat;