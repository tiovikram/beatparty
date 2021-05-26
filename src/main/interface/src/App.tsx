import React, {Component} from 'react';

import Song from './Song';
import InputFormUploadSong from "./InputFormUploadSong";

import "./App.css";

interface AppState{
    songList: [];


    uploadButtonPressed: boolean;
}

/*
The App component is the main component for Front-End webpage. It uses AppState as its state and uses no Props.

This component is responsible for retrieving song data from the back-end of the application and
rendering it on the webpage.

*/
class App extends Component<{}, AppState>{

    //create constructor here
    constructor(props: any){
        super(props);
        this.state = {
            songList: [],
            uploadButtonPressed: false,
        }
        this.getSongData();
    }

    getSongData = async () => {
        let extendPath = 'http://13.87.246.41:8080/getSongs/12';
        try {
            let response = await fetch(extendPath);
            if (!response.ok) {
                alert("Could not receive song data");
                return;
            }
            let allSongs = await response.json();
            console.log(allSongs);
            this.setState({
                songList: allSongs,
                uploadButtonPressed: false,
            })
        } catch (e) {
            alert("There was an error contacting the server.");
            console.log(e);
        }
    };

    displayPopUp(clicked: boolean){
        alert("pop up here");
    }

    getShuffledSongs = async () =>{
        let extendPath = 'http://13.87.246.41:8080/getShuffledSongs/12';
        try {
            let response = await fetch(extendPath);
            if (!response.ok) {
                alert("Could not receive song data");
                return;
            }
            let shuffleSongs = await response.json();
            console.log(shuffleSongs);
            this.setState({
                songList: [],
                uploadButtonPressed: false,
            })
            this.setState({
                songList: shuffleSongs,
                uploadButtonPressed: false,
            })
        } catch (e) {
            alert("There was an error contacting the server.");
            console.log(e);
        }
    }

    getTopSongs = async () =>{
        //alert("Top");
        let extendPath = 'http://13.87.246.41:8080/getSongs/12';
        try {
            let response = await fetch(extendPath);
            if (!response.ok) {
                alert("Could not receive song data");
                return;
            }
            let topSongs = await response.json();
            this.setState({
                songList: [],
                uploadButtonPressed: false,
            })
            this.setState({
                songList: topSongs,
                uploadButtonPressed: false,
            })
            console.log(this.state.songList);
        } catch (e) {
            alert("There was an error contacting the server.");
            console.log(e);
        }
    }

    uploadSong = () =>{
        this.setState({
            uploadButtonPressed: true,
        })
    }

    notUploadingAnymore = () =>{
        this.setState({
            uploadButtonPressed: false,
        });
    }

    onSignIn = (googleUser: any) => {
        var profile = googleUser.getBasicProfile();
        console.log('ID: ' + profile.getId()); // Do not send to your backend! Use an ID token instead.
        console.log('Name: ' + profile.getName());
        console.log('Image URL: ' + profile.getImageUrl());
        console.log('Email: ' + profile.getEmail()); // This is null if the 'email' scope is not present.
    }

    render() {
        if (this.state.uploadButtonPressed){
            return(
                <div>
                <h1 style={{ marginLeft: '40.5rem' }} >
                  BeatParty!
                  </h1>
                  <div style = {{ marginLeft: '36.5rem'}}>
                      <button onClick={this.getShuffledSongs}>Shuffle</button>
                      <button onClick={this.getTopSongs}>See Top Songs</button>
                      <button onClick={this.uploadSong}>Upload a Song</button>
                  </div>
                <InputFormUploadSong onChange={this.notUploadingAnymore}/>
                <div className="feedback">
                    <p>
                        <a href="https://forms.gle/Q8QB87Eud7BMbkBg8">Report a bug or give feedback</a>
                    </p>
                </div>

                </div>
                            );
        }
        else{
            const songsToRender = [];
            for (const currentSong of this.state.songList){
                songsToRender.push(<Song   id={currentSong['id']}
                                           artist={'by ' + currentSong['artistName']}
                                           date={currentSong['uploadDate']}
                                           name={currentSong['name']}
                                           upvotes={currentSong['votes']}
                                           url={currentSong['songLink']}
                                   />);

            }
            console.log("Songs to Render:" + songsToRender);
            return (
		<div className="App" >
			<meta name="google-signin-client_id" content="135607733919-k99bm0ldihbmko0hkg4b3pfmp3dj6ue9.apps.googleusercontent.com"></meta>
			<h1 style={{ marginLeft: '40.5rem' }} >
				BeatParty!
			</h1>
			<div style = {{ marginLeft: '36.5rem'}}>
				<button onClick={this.getShuffledSongs}>Shuffle</button>
				<button onClick={this.getTopSongs}>See Top Songs</button>
				<button onClick={this.uploadSong}>Upload a Song</button>
				<div class="g-signin2" data-onsuccess="onSignIn"></div>
			</div>
			<ol style = {{}}>

				{songsToRender.map(song => (
				<li className="song-list">
					{song}
				</li>
				))}

			</ol>

			<div className="feedback">
				<p> <a href="https://forms.gle/Q8QB87Eud7BMbkBg8">Report a bug or give feedback</a> </p>
			</div>
			<script src="https://apis.google.com/js/platform.js" async defer></script>
                        <script>
				function onSignIn(googleUser) {
					let profile = googleUser.getBasicProfile();
					console.log('ID: ' + profile.getId()); // Do not send to your backend! Use an ID token instead.
					console.log('Name: ' + profile.getName());
					console.log('Image URL: ' + profile.getImageUrl());
					console.log('Email: ' + profile.getEmail()); // This is null if the 'email' scope is not present.
				}
                        </script>
		</div>
            );
        }
    }
}
export default App;
