import React, {Component} from 'react';

interface InputFormUploadSongProps{
    onChange(): void;
}

interface InputFormUploadSongState{
    newSongName: string;
    newArtistName: string;
    newURL: string;
}

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
        //alert(this.state.newSongName + " " + this.state.newArtistName + " " + this.state.newURL);
        var newSongObj = { artistName: this.state.newArtistName, date: new Date().toString(), songName: this.state.newSongName, upvotes: 0, url: this.state.newURL };
        var myJSON = JSON.stringify(newSongObj);
        try{
            var xhr = new XMLHttpRequest();
            xhr.open("POST", 'http://localhost:8080/uploadNewSong', true);
            xhr.setRequestHeader('Content-Type', 'application/json');
            xhr.send(myJSON);
        }
        catch (e){
            alert("There was an error contacting the server.");
            console.log(e);
        }
    }
    //single input form song link only
    //validate button - fetch song data from spotify directly
    //store data from spotify and send to back-end using stringify

    render(){
        return (
          <div style = {{ marginLeft: '36.5rem'}}>
          <form>
            <h1>Upload A Song</h1>
            <p>Enter song name:</p>
            <input type="text" onChange={this.updateSongName}/>
            <p>Enter artist name:</p>
            <input type="text" onChange={this.updateArtistName}/>
            <p>Enter song URL:</p>
            <input type="text" onChange={this.updateURL}/>
          </form>
          <button style = {{ marginLeft: '-3.5rem'}} onClick={this.backToHomePage}>Return to Home Page</button>
          <button onClick={this.submitSong}>Submit Song Selection</button>
          </div>
        );
    }
}
export default InputFormUploadSong;