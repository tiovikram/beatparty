import React, {Component} from 'react';

interface TitleAndButtonsProps{
    uploadButtonPressed: boolean;
    onChange(clicked: boolean): void;
}

class TitleAndButtons extends Component<TitleAndButtonsProps, {}>{

    getShuffledSongs = () =>{
        alert("Shuffled");
    }

    getTopSongs = () =>{
        alert("Top");
    }

    uploadSong = () =>{
        alert("Uploading");
        this.props.onChange(true);
    }

    render(){
        return(
            <>
            <h1 style={{ marginLeft: '40.5rem' }} >
            BeatParty!
            </h1>
            <div style = {{ marginLeft: '36.5rem'}}>
                <button onClick={this.getShuffledSongs}>Shuffle</button>
                <button onClick={this.getTopSongs}>See Top Songs</button>
                <button onClick={this.uploadSong}>Upload a Song</button>
            </div>
            </>
        );
    }

}

export default TitleAndButtons;