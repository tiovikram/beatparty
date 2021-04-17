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
    //create constructor here
    render(){
        return(
            <>
            <h1>
            BeatParty!
            </h1>
            <div>
                <button onClick={this.getShuffledSongs}>Shuffle</button>
                <button onClick={this.getTopSongs}>See Top Songs</button>
                <button onClick={this.uploadSong}>Upload a Song</button>
            </div>
            </>
        );
    }

}
export default TitleAndButtons;