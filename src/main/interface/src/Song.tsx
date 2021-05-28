import React, {Component} from 'react';

import "./Song.css"

interface SongState {
    isVoted: boolean;
    upvotes: number;
    //user: any;
}

// TODO: Add props for isVoted, imageUrl
interface SongProps {
    id: number;
    artist: string;
    date: string;
    name: string;
    upvotes: number;
    url: string;
    isVoted: boolean;
    user: any;
}

/*
The Song component outlines a Song object It uses SongState as its state and uses SongProps for props.

This component is responsible for storing and rendering song data in a visually-friendly format.

*/
class Song extends Component<SongProps, SongState> {

    constructor(props: any) {
        super(props);

        this.state = {
            isVoted: this.props.isVoted,
            upvotes: this.props.upvotes,
            //user: this.props.user,
        };
    }

    upvote = (isVoted: boolean, upvotes: number) => {
        if (this.props.user !== null) {
            if (!isVoted) {
                this.setState({
                    isVoted: true,
                    upvotes: upvotes + 1
                });
                try{
                    var requestToUpvote = new XMLHttpRequest();
                    requestToUpvote.open("POST", 'http://localhost:8080/vote/' + (this.props.id).toString() + '/' + (true).toString(), true);
                    requestToUpvote.setRequestHeader('Content-Type', 'application/json');
                    requestToUpvote.send();
                }
                catch (e){
                    alert("There was an error contacting the server.");
                    console.log(e);
                }
            } else {
                this.setState({
                    isVoted: false,
                    upvotes: upvotes - 1
                })
                try{
                    var requestToDownvote = new XMLHttpRequest();
                    requestToDownvote.open("POST", 'http://localhost:8080/vote/' + (this.props.id).toString() + '/' + (false).toString(), true);
                    requestToDownvote.setRequestHeader('Content-Type', 'application/json');
                    requestToDownvote.send();
                }
                catch (e){
                    alert("There was an error contacting the server.");
                    console.log(e);
                }
            }
        }
    }

    render() {
        var buttonGray = {}
        if (!this.state.isVoted) {
           buttonGray = {filter: "grayscale(100%)"}
        }

        console.log(this.props.id);
        console.log(this.props.artist);
        console.log(this.props.upvotes);
        return (
        <div className="song-container">
            <div className="upvote-section">
                <button className="upvote-button"
                        style={buttonGray}
                        onClick={() => this.upvote(this.state.isVoted, this.state.upvotes)}>
                    <span role="img" aria-label="thumbs-up">👍</span>
                </button>
                <div className="upvote-counter">
                    {this.state.upvotes}
                </div>
            </div>
            <a href={this.props.url} target={"_blank"} ref={"no referrer noopener"}>
                <div className="song-info">
                    <div className="song-name">
                        {this.props.name}
                    </div>
                    <div className="song-metadata">
                        <div className="artist">
                            {this.props.artist}
                        </div>
                        <div className="date">
                            {this.props.date}
                        </div>
                    </div>
                </div>
            </a>
        </div>
        )
    }
}

export default Song;
