import React, {Component} from 'react';

import "./Song.css"

interface SongState {
    isVoted: boolean;
    upvotes: number;
}

// TODO: Add props for isVoted, imageUrl
interface SongProps {
    artist: string;
    date: string;
    name: string;
    upvotes: number;
    url: string;
}

class Song extends Component<SongProps, SongState> {

    constructor(props: any) {
        super(props);

        this.state = {
            isVoted: false,
            upvotes: this.props.upvotes
        };
    }

    upvote = (isVoted: boolean, upvotes: number) => {
        if (!isVoted) {
            this.setState({
                isVoted: true,
                upvotes: upvotes + 1
            });
        } else {
            this.setState({
                isVoted: false,
                upvotes: upvotes - 1
            })
        }
    }

    render() {
        var buttonGray = {}
        if (!this.state.isVoted) {
           buttonGray = {filter: "grayscale(100%)"}
        }
        
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
            <a href={this.props.url}>
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
