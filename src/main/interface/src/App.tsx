import React, {Component} from 'react';
import "./App.css";
import TitleAndButtons from "./TitleAndButtons";
import Song from './Song';

interface AppState{
    songList: Song[];
}

class App extends Component<{}, AppState>{

    //create constructor here
    constructor(props: any){
        super(props);
        this.state = {
            songList: [],
        }
        //this.getSongList();
    }

    componentDidMount = () => {
        this.getSongList();
    }

    getSongList = () => {
        var song1 = new Song ('Bohemian Rhapsody', 'Queen', 5, 'https://open.spotify.com/album/7C2DKB8C12LqxMkfJRwTo9?highlight=spotify:track:6l8GvAyoUZwWDgF1e4822w');
        //song.getName(); //for testing only
        var song2 = new Song ('Thriller', 'Michael Jackson', 4, 'link here');
        this.setState({
            songList: [...this.state.songList, song1, song2]
        });
    }

    goToGithub = () => {
        window.location.href = "https://www.google.com";
    }

    upvote = () => {
        alert("Voted");
    }

    render() {
        const final = [];
        for (let currentSong of this.state.songList) {
          final.push(
                    <div>
                    <a href = {currentSong.getLink()}>
                    <li key={currentSong.getName()}>{currentSong.getName() + " by " + currentSong.getArtist()}</li></a>
                    </div>
                    );
        }
        return (
          <div className="App">
            <TitleAndButtons/>
            <ol>{final}</ol>
          </div>
        );
      }

    /*
    render(){
        return(
            <>
            <div>
            <TitleAndButtons/>
            <p> Testing Render here</p>
            <button onClick={this.goToGithub}>Click to go to Github</button>
            </div>
            </>
        );
    }
    */
}
export default App;