import React, {Component} from 'react';

import TitleAndButtons from "./TitleAndButtons";
import Song from './Song';

import "./App.css";

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

    render() {
        return (
          <div className="App" >
              <TitleAndButtons/>
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
export default App;
