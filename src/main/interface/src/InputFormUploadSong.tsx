import React, {Component} from 'react';

interface InputFormUploadSongProps{
    onChange(): void;
}

interface InputFormUploadSongState{
    newSongName: string;
    newArtistName: string;
    newURL: string;
}

/*
The InputFormUploadSong component controls the 'upload song' functionality.
It uses InputFormUploadSongState as its state and uses InputFormUploadSongProps for props.

This component is responsible for rendering the input form for uploading new songs,
parsing input from the form, and transferring the new song data to the back-end of our application.
*/
class InputFormUploadSong extends Component<InputFormUploadSongProps, InputFormUploadSongState>{

    constructor(props: any){
        super(props);
        this.state = {
            newSongName: '',
            newArtistName: '',
            newURL: '',
        }
    }

    backToHomePage = () =>{
        this.props.onChange();
    }

    updateSongName = (event: React.ChangeEvent<HTMLInputElement>) => {
        this.setState({
            newSongName: event.target.value,
        })
    }

    updateArtistName = (event: React.ChangeEvent<HTMLInputElement>) => {
        this.setState({
            newArtistName: event.target.value,
        })
    }

    updateURL = (event: React.ChangeEvent<HTMLInputElement>) => {
        this.setState({
            newURL: event.target.value,
        })
    }

    submitSong = () =>{
        var regexes = [];
        regexes.push(/https:\/\/open\.spotify\.com\/track\/\w{22}/i);
        regexes.push(/https:\/\/www\.youtube\.com\/watch\?v=.{11}/i);
        regexes.push(/https:\/\/www\.soundcloud\.com\/.+\/.+/i);
        regexes.push(/https:\/\/youtube\.com\/watch\?v=.{11}/i);
        regexes.push(/https:\/\/soundcloud\.com\/.+\/.+/i);
        regexes.push(/https:\/\/youtu\.be\/.{11}/i);
        var matchesAny = false;
        for (var i = 0; i < regexes.length; i++) {
            if (regexes[i].test(this.state.newURL)) {
                matchesAny = true;
            }
        }

        if (matchesAny) {
        var newSongObj = { artistName: this.state.newArtistName, name: this.state.newSongName, songLink: this.state.newURL };
        var myJSON = JSON.stringify(newSongObj);
        console.log(myJSON);
        try{
            var xhr = new XMLHttpRequest();
            //xhr.open("POST", 'http://13.87.246.41:8080/uploadNewSong', true);
	    xhr.open("POST", 'http://localhost:8080/uploadNewSong', true);
            xhr.setRequestHeader('Content-Type', 'application/json');
            xhr.send(myJSON);
        }
        catch (e){
            alert("There was an error contacting the server.");
            console.log(e);
        }
        alert("New Song Upload! You can now continue browsing music! You may have to login again.");
        //window.location.href = "http://beatparty.azurewebsites.net";
	window.location.href = "http://localhost:3000";

        } else {
            alert("URL is not a valid Spotify, Youtube or Soundcloud URL");
        }        
    }
    //single input form song link only
    //validate button - fetch song data from spotify directly
    //store data from spotify and send to back-end using stringify

    render(){
        return (
          <div style = {{ marginLeft: '38.5rem'}}>
          <form>
            <h1>Upload A Song</h1>
            <p>Enter song name:</p>
            <input type="text" onChange={this.updateSongName}/>
            <p>Enter artist name:</p>
            <input type="text" onChange={this.updateArtistName}/>
            <p>Enter song URL:</p>
            <input type="text" onChange={this.updateURL}/>
          </form>
          <button onClick={this.submitSong}>Submit Song Selection</button>
          </div>
        );
    }
}
export default InputFormUploadSong;
