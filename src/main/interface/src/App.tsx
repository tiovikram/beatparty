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
    }

    render(){
        return(
            <div>
            <TitleAndButtons/>
            <p> Testing Render here</p>
            </div>
        );
    }
}
export default App;