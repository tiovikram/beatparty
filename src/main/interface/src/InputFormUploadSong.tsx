import React, {Component} from 'react';

interface InputFormUploadSongProps{
    onChange(): void;
}

class InputFormUploadSong extends Component<InputFormUploadSongProps, {}>{

    backToHomePage = () =>{
        this.props.onChange();
    }

    submitSong = () =>{
        alert("Top");
    }

    render(){
        return (
          <div>
          <form>
            <h1>Upload A Song</h1>
            <p>Enter song name:</p>
            <input
              type="text"
            />
            <p>Enter artist name:</p>
            <input
              type="text"
            />
          </form>
          <button onClick={this.backToHomePage}>Return to Home Page</button>
          <button onClick={this.submitSong}>Submit Song Selection</button>
          </div>
        );
    }
}
export default InputFormUploadSong;