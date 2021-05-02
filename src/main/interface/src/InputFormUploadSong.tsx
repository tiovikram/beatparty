import React, {Component} from 'react';

class InputFormUploadSong extends Component<{}, {}>{

    render(){
        return (
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
        );
    }
}
export default InputFormUploadSong;