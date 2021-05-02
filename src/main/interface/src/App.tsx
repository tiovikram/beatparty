import React, {Component} from 'react';

//import TitleAndButtons from "./TitleAndButtons";
import Song from './Song';
import InputFormUploadSong from "./InputFormUploadSong";

import "./App.css";

interface AppState{
    songList: Song[];
    uploadButtonPressed: boolean;
}

class App extends Component<{}, AppState>{

    //create constructor here
    constructor(props: any){
        super(props);
        this.state = {
            songList: [],
            uploadButtonPressed: false,
        }
    }

    displayPopUp(clicked: boolean){
        alert("pop up here");
    }

    getShuffledSongs = () =>{
        alert("Shuffled");
    }

    getTopSongs = () =>{
        alert("Top");
    }

    uploadSong = () =>{
        alert("Uploading");
        //this.props.uploadButtonClicked(true);
        this.setState({
            uploadButtonPressed: true,
        })
    }

    render() {
        if (this.state.uploadButtonPressed){
            return(
                <InputFormUploadSong/>
            );
        }
        else{
            return (
              <div className="App" >
                  <h1 style={{ marginLeft: '40.5rem' }} >
                  BeatParty!
                  </h1>
                  <div style = {{ marginLeft: '36.5rem'}}>
                      <button onClick={this.getShuffledSongs}>Shuffle</button>
                      <button onClick={this.getTopSongs}>See Top Songs</button>
                      <button onClick={this.uploadSong}>Upload a Song</button>
                  </div>
                    <ol style = {{}}>
                        <Song artist={"Scorpions"}
                                date={new Date().toString()}
                                name={"Blackout"}
                                upvotes={0}
                                url={"https://open.spotify.com/track/15RpfmFhrE5RRkf4vZ6kZu?si=153e8b2d8d8341d7"}
                        />
                        <Song artist={"DJ Snake"}
                                date={new Date().toString()}
                                name={"Try Me (with Plastic Toy)"}
                                upvotes={0}
                                url={"https://open.spotify.com/track/5vTtANNlQK5UhwfooDek5y?si=eb537289f7f84d74"}
                        />
                    </ol>
              </div>
            );
        }
    }
}
export default App;
