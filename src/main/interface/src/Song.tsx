//import React, {Component} from 'react';
class Song{
    songName: string;
    artistName: string;
    numVotes: number;
    link: string;
    //create constructor here

    constructor(nameOfSong: string, nameOfArtist: string, votes: number, linkToSong: string){
        this.songName = nameOfSong;
        this.artistName = nameOfArtist;
        this.numVotes = votes;
        this.link = linkToSong;
    }

    getName = () =>{
        return this.songName;
    }

    getArtist = () =>{
        return this.artistName;
    }

    getLink = () =>{
        return this.link;
    }

}
export default Song;