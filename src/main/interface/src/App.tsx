import React, {Component} from 'react';
import {GoogleLogin} from 'react-google-login';

import Song from './Song';
import InputFormUploadSong from "./InputFormUploadSong";

import "./App.css";

interface AppState{
    songList: any;
    user: any;
    upvotedSongIds: any;
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
            user: null,
            upvotedSongIds: [], 
            uploadButtonPressed: false,
        }
        this.getSongData();
    }

    getSongData = async () => {
        //let extendPath = 'http://13.87.246.41:8080/getSongs/12';
	let extendPath = 'http://localhost:8080/getSongs/12';
        try {
            let response = await fetch(extendPath);
            if (!response.ok) {
                alert("Could not receive song data");
                return;
            }
            let allSongs = await response.json();
            let listOfSongs = this.createSongList(allSongs);
            this.setState({
                songList: listOfSongs,
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
        //let extendPath = 'http://13.87.246.41:8080/getShuffledSongs/12';
	let extendPath = 'http://localhost:8080/getShuffledSongs/12';

        try {
            let response = await fetch(extendPath);
            if (!response.ok) {
                alert("Could not receive song data");
                return;
            }
            let shuffleSongs = await response.json();
            this.setState({
                songList: [],
                uploadButtonPressed: false,
            })
            let listOfSongs = this.createSongList(shuffleSongs);
            this.setState({
                songList: listOfSongs,
                uploadButtonPressed: false,
            })
            this.getUpvotedList();
            this.mergeUpvotedSongs(this.state.upvotedSongIds);
        } catch (e) {
            alert("There was an error contacting the server.");
            console.log(e);
        }
    }

    getTopSongs = async () =>{
        //let extendPath = 'http://13.87.246.41:8080/getSongs/12';
	let extendPath = 'http://localhost:8080/getSongs/12';

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
            let listOfSongs = this.createSongList(topSongs);
            this.setState({
                songList: listOfSongs,
                uploadButtonPressed: false,
            })
            this.getUpvotedList();
            this.mergeUpvotedSongs(this.state.upvotedSongIds);
        } catch (e) {
            alert("There was an error contacting the server.");
            console.log(e);
        }
    }

    createSongList =  (songsToParse: any) => {
        const listOfSongs = [];
        for (const currentSong of songsToParse){
            var song = {
                id: currentSong.id,
                artist: currentSong.artistName,
                date: currentSong.uploadDate,
                name: currentSong.name,
                upvotes: currentSong.votes,
                url: currentSong.songLink,
                isVoted: false,
                user: this.state.user,
            };
            listOfSongs.push(song);
        }
        return listOfSongs;
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

    successResponseGoogle = async (response: any) => {
        alert("Signed in to Google successfully!");
        this.setState({
            user: response.tokenId,
        });

        // TODO: Get list of already upvoted songs from the backend and merge
        // TODO: Loop through SongList and if the name and artist matches, update the isUpvoted.
	// var song = new Song;
        // song.id =  list.getElement(i).id
        // ...
        // ...

	this.getUpvotedList();
	this.mergeUpvotedSongs(this.state.upvotedSongIds);
    }

    getUpvotedList = async () => {
        //let extendPath = 'http://13.87.246.41:8080/getSongsVotedByUser/' + this.state.user.toString();
        if (this.state.user !== null){
            let extendPath = 'http://localhost:8080/getSongsVotedByUser/' + this.state.user.toString();

            try {
                let response = await fetch(extendPath);
                if (!response.ok) {
                    alert("Could not receive data after login");
                    return;
                }
                let votedSongs = await response.json();
                this.setState ({
                    upvotedSongIds: []
                })
                this.setState({
                    upvotedSongIds: votedSongs
                });

            } catch (e) {
                alert("There was an error contacting the server.");
                console.log(e);
            }
        }
    }

    mergeUpvotedSongs = (upvotedSongs: any) => {
        var updatedList: any = JSON.parse(JSON.stringify(this.state.songList));
        for (var song of updatedList) {
            if (upvotedSongs.includes(song.id)) {
                song.isVoted = true;
            }
        }
        
        this.setState({
            songList: []
        });
        
        this.setState({
            songList: updatedList
        });
    }


    failureResponseGoogle = (response: any) => {
        alert("Error: Google sign-in failed");
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
                                           artist={'by ' + currentSong['artist']}
                                           date={currentSong['date']}
                                           name={currentSong['name']}
                                           upvotes={currentSong['upvotes']}
                                           url={currentSong['url']}
                                           isVoted={currentSong['isVoted']}
                                           user={this.state.user}
                                   />);

            }
            return (
		<div className="App" >
			<h1 style={{ marginLeft: '40.5rem' }} >
				BeatParty!
			</h1>
			<div style = {{ marginLeft: '36.5rem'}}>
				<button onClick={this.getShuffledSongs}>Shuffle</button>
				<button onClick={this.getTopSongs}>See Top Songs</button>
				<button onClick={this.uploadSong}>Upload a Song</button>
				<GoogleLogin
    					clientId="135607733919-k99bm0ldihbmko0hkg4b3pfmp3dj6ue9.apps.googleusercontent.com"
					buttonText="Login with Google"
					onSuccess={this.successResponseGoogle}
					onFailure={this.failureResponseGoogle}
					cookiePolicy={'single_host_origin'}
				/>
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
		</div>
            );
        }
    }
}
export default App;


