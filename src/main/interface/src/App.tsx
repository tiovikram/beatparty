import React, {Component} from 'react';

//import TitleAndButtons from "./TitleAndButtons";
import Song from './Song';
import InputFormUploadSong from "./InputFormUploadSong";

import "./App.css";

interface AppState{
    //songList: Song[];

    songList: [];

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

    //function to get new data from Back-End
    componentDidMount(){
        //populate songList with data from Back-End.
        this.getSongData();
    }

    getSongData = async () => {
       // /*
        let extendPath = 'http://localhost:8080/getSongs/2';
        try {
            let response = await fetch(extendPath);
            if (!response.ok) {
                alert("Could not receive song data");
                return;
            }
            let allSongs = await response.json();
            this.setState({
                songList: allSongs,
            })

        } catch (e) {
            alert("There was an error contacting the server.");
            console.log(e);
        }
        //*/
    };

    displayPopUp(clicked: boolean){
        alert("pop up here");
    }

    getShuffledSongs = async () =>{
        //alert("Shuffled");
        ///*
        let extendPath = 'http://localhost:8080/getShuffledSongs/2';
        try {
            let response = await fetch(extendPath);
            if (!response.ok) {
                alert("Could not receive song data");
                return;
            }
            let allSongs = await response.json();
            this.setState({
                songList: allSongs,
            })

        } catch (e) {
            alert("There was an error contacting the server.");
            console.log(e);
        }
        //*/
    }

    getTopSongs = async () =>{
        //alert("Top");
        let extendPath = 'http://localhost:8080/getSongs/20';
        try {
            let response = await fetch(extendPath);
            if (!response.ok) {
                alert("Could not receive song data");
                return;
            }
            let allSongs = await response.json();
            this.setState({
                songList: allSongs,
            })

        } catch (e) {
            alert("There was an error contacting the server.");
            console.log(e);
        }
    }

    uploadSong = () =>{
        //alert("Uploading");
        //this.props.uploadButtonClicked(true);
        this.setState({
            uploadButtonPressed: true,
        })
    }

    notUploadingAnymore = () =>{
        this.setState({
            uploadButtonPressed: false,
        });
    }

    render() {
        if (this.state.uploadButtonPressed){
            return(
                <InputFormUploadSong onChange={this.notUploadingAnymore}/>
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
