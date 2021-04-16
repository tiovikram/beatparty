import React, {Component} from 'react';
import "./App.css";
import Song from './Song';

interface AppState{
    songList: Song[];
}

class App extends Component<{}, AppState>{

    //create constructor here

    render(){
        return(
        <div>
        <p> Testing Render </p>
        </div>
        );
    }
}
export default App;