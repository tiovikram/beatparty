import React, {Component} from 'react';
class TitleAndButtons extends Component<{}, {}>{

    getShuffledSongs = () =>{
        alert("Shuffled");
    }

    getTopSongs = () =>{
        alert("Top");
    }

    uploadSong = () =>{
        alert("Uploading");
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