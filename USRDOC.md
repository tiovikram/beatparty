## User Documentation

### Table of Contents 
1. [A High Level description](#a-high-level-description)  
   1.1 [What is BeatParty](#what-is-beatparty)  
   1.2 [Why would someone use the BeatParty platform](#why-would-someone-use-the-beatparty-platform)  
2. [How to install the software](#how-to-install-the-software)  
3. [How to run the software](#how-to-run-the-software)  
4. [How to use the software](#how-to-use-the-software)  
   4.1 [Use Case 1: Seeing a list of top-rated songs](#use-case-1)  
   4.2 [Use Case 2: Seeing a list of songs regardless of their ratings](#use-case-2)  
   4.1 [Use Case 3: Liking songs on BeatParty](#use-case-3)  
   4.1 [Use Case 4: Uploading new songs to BeatParty](#use-case-4)  
   4.1 [Use Case 5: Providing feedback to the developers of BeatParty](#use-case-5)  
5. [How to report a bug](#how-to-report-a-bug)
6. [Known Bugs](#known-bugs)  
  
### A High Level description
#### What is BeatParty
BeatParty is a music sharing/pop chart platform where users can upload, discover, and vote on new music in an unbiased way. BeatParty helps niche music become more popular just like how specialty record labels did in the 20th Century.

#### Why would someone use the BeatParty platform
BeatParty can be used for various reasons by various people. Some of its main attractions are:
1. Easy access to high-quality music regardless of source
2. Quick, unbiased discovery of new music
3. Hassle-free uploading to the platform for musicians

BeatParty lets the users be the ones who decide what’s popular. With music charts like Billboard, the big record labels decide which songs become popular. BeatParty cuts out biased middle men and enables music fans to decide for themselves what music is great, regardless of source.  
> "Not everyone can become a great artist, but a great artist can come from anywhere!" - Anton Ego, Ratatouille (2007)
  
### How to install the software
1. Clone the `beatparty` repository
   - Using HTTPS:  
      `git clone https://github.com/v-subbiah/beatparty.git`  
   - Using SSH:  
       ⚠️  Note: SSH keys for your local machine need to be setup on GitHub prior to cloning  
      `git clone git@github.com:v-subbiah/beatparty.git`  
2. Install npm
[Instructions to install npm](https://www.npmjs.com/get-npm)
3. Install Maven  
[Instructions to install Maven](https://maven.apache.org/install.html)  

### How to run the software
1. Change your working directory to the `src/main/interface` directory 
```
   $ cd src/main/interface
```
2. Install the dependencies for the front-end application
```
   $ npm install
```
3. Run the npm development server on your local machine
```
   $ npm start
```
4. In a different terminal, ensure that your working directory is the root directory of the repository.
```
   $ pwd
   <local_filepath>/beatparty/
```
5. Run the maven wrapper file to build and run the backend
```
   $ ./mvnw spring-boot:run
```
6. Go to `http://localhost:3000` in your favorite browser and enjoy!

### How to use the software

The BeatParty application has four main functions for end-users:
  
#### Use Case 1
**Seeing a list of top-rated songs**  
> Music fans can see a list of the top-rated songs chosen by their peers by clicking the button that says ‘See Top Songs’ at the top of the web page. The songs are ranked by the number of votes given to them by other users of the BeatParty application. 
  
1. From any page, click the top songs button
  
![](https://user-images.githubusercontent.com/45928972/118581582-b956cf80-b746-11eb-9815-8327346f3aa6.png)  
  
2. The top songs are shown sorted in descending order of number of votes
  
![](https://user-images.githubusercontent.com/45928972/118581590-bb209300-b746-11eb-9811-7be63b4550af.png)  
  
#### Use Case 2
**Seeing a list of songs regardless of their ratings**  
> Music fans can discover new music regardless of rating by clicking the ‘Shuffle’ button at the top of the web page. Discovering new music that may not currently have the highest ratings using the application allows music fans to organically become early adopters of new music and promote it amongst their friends. Unlike other music platforms, BeatParty helps music fans discover new music in an unbiased manner: our 'Shuffle' function presents music fans with a truly random set of songs and does not promote certain types of music over others.
  
1. Click the shuffle button from any page  
  
![](https://user-images.githubusercontent.com/45928972/118581594-bcea5680-b746-11eb-96c2-0722b5c186f2.png)  
  
2. View a list of songs in shuffled order 
   
![](https://user-images.githubusercontent.com/45928972/118581596-bd82ed00-b746-11eb-87d0-7f7296a149de.png)
  
#### Use Case 3  
**Liking songs on BeatParty**  
> Music fans can promote a song on the BeatParty application by 'upvoting' a particular song. To 'upvote' a particular song on BeatParty, a music fan can simply click on the "thumbs-up" icon next to the song that the fan likes. Once a music fan clicks on the "thumbs-up" icon to upvote a song, the icon will turn yellow, indicating that the fan has successfully upvoted the song. If a music fan decides they no longer want to be upvote a certain song, they can click on the "thumbs-up" button again to remove their upvote the song. Once a music fan clicks on the "thumbs-up" icon to remove an upvote for a song, the icon will turn gray, indicating that the fan has successfully removed their upvote for a song.  
  
1. Click the upvote element's upvote button to upvote a song  
   *Note:* the upvote button is the thumbs up symbol, whereas the number below registers the number of upvotes  
  
![](https://user-images.githubusercontent.com/45928972/118581600-be1b8380-b746-11eb-9301-167d45aa85c2.png)  
  
2. The song has been upvoted (check the upvote counter)  
  
![](https://user-images.githubusercontent.com/45928972/118581603-beb41a00-b746-11eb-9682-e1230e08a153.png)  
  
3. Click the upvote button again to undo the upvote  
  
![](https://user-images.githubusercontent.com/45928972/118582192-cc1dd400-b747-11eb-96ef-c88072348582.png)  
  
4. The upvote has been undone (check the upvote counter)  
  
![](https://user-images.githubusercontent.com/45928972/118581604-bf4cb080-b746-11eb-9779-efdc763ecfd4.png)
  
#### Use Case 4  
**Uploading new songs to BeatParty**   
> Music fans can upvote new music to BeatParty by clicking on the “Upload a Song” button. Clicking on the "Upload a Song" button reveals 3 fields that allow a fan to input the name of a song, the name of the artist who performed the song, and a link to the song. Once a music fan enters the name of the song they want to upload, the artist who performed the song, and a link to the song, they can click on the “Submit Song Selection” button to add the song to the BeatParty platform. Now that the song is stored on the BeatParty platform, other music fans can now listen to and upvote the newly uploaded song.  
  
1. Click the upload a song button  
  
![](https://user-images.githubusercontent.com/45928972/118582887-fc19a700-b748-11eb-8311-698f655f6ef0.png)  
  
2. Fill in the name of the song, the artist name and the URL (only Spotify, SoundCloud and Youtube URLs are accepted) respectively  
  
![](https://user-images.githubusercontent.com/45928972/118582889-fcb23d80-b748-11eb-938b-4cb212fa45f6.png)  
  
3. Click the submit a song button  
  
![](https://user-images.githubusercontent.com/45928972/118582891-fd4ad400-b748-11eb-9045-f5e856edb701.png)  
  
4. Redirected to the top songs page and the song has been uploaded  
  
![](https://user-images.githubusercontent.com/45928972/118582892-fde36a80-b748-11eb-9214-6ff9ca00b439.png)  
  
#### Use Case 5  
**Providing feedback to the developers of BeatParty**  
> Music fans can provide feedback to the developers of BeatParty by clicking on the "Give Feedback" button. 
Clicking on the "Give Feedback" button redirects music fans to a form that allows users to submit feedback to the software development
team that builds the BeatParty applications.  
  
1. Click the link to the bug reporting and feedback form from any page  
  
![](https://user-images.githubusercontent.com/45928972/118584575-1ef98a80-b74c-11eb-9c36-08c20ff2b2b1.png)  
  
2. Application redirects to the bug reporting and feedback form url  
  
![](https://user-images.githubusercontent.com/45928972/118584576-1f922100-b74c-11eb-8126-578011bde6cb.png)  
  
### How to report a bug
Bugs can be reported using [this form](https://forms.gle/Q8QB87Eud7BMbkBg8)
  
### Known Bugs
Known bugs and their statuses can be viewed [here](https://docs.google.com/spreadsheets/d/16J0mC41MM_DhOiK8356yEZN4aLCUgVXj2tZnjuu54zc/edit?usp=sharing)

