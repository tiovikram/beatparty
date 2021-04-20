## Beat Party ![](https://travis-ci.com/v-subbiah/beatparty.svg?token=MWpStGKXXEjsTeLgMJyz&branch=master)
  
A CSE 403: Software Engineering (2021 Spring) group project  
  
### Table of Contents:
---
1. [Ideas And Goals](#ideas-and-goals)  
  1.1 [Idea](#idea)  
  1.2 [Goals](#goals)  
  1.3 [Stretch Goals](#stretch-goals)  
2. [Project Structure](#project-structure)  
  2.1 [Source Code](#source-code)  
  2.2 [Build and Run instructions](#build-and-run-instructions)  
  2.3 [Database Resources](#database-resources)  
3. [Version Control Guidelines](#version-control-guidelines)  
  3.1 [Cloning](#cloning)  
  3.2 [Committing](#committing)  
  3.3 [Pushing](#pushing)  
  
### Ideas and Goals
---
#### Idea
BeatParty is a community-based music discovery service that combines ephemeral front page rankings with a music publishing platform. With an immediate feedback loop from voting on songs, music fans on the platform can engage with undiscovered new music and artists as they rise in ranking.  
  
#### Goals
Develop a web-application that offers the following features to users.  
1. Creators can upload their original music on the website [may be changed to Spotify and SoundCloud links due to possible copyright issues]
2. Users can upvote or downvote songs to provide an organic ranking to their popularity
3. Users can view the songs listed on the platform by their user-determined ranking
4. Listeners can discover random songs listed on the platform
  
#### Stretch Goals:
The stretch goals for the application features are as follows.
1. ~~Stream music within the web application~~ [Listing streaming options via Spotify and SoundCloud allows the third-party music streaming service to handle streaming infrastructure]
2. Integration with social media platforms (Facebook, Instagram, ~~Spotify~~ [service already used as described above], Twitter)
3. Private party rooms where partygoers can vote on which song to play next
   
### Project Structure
---
#### Source Code
**master:**  
Back-end Java Functionality: `/src/main/java/com/beatparty/`  
Spring Boot Application main point: `src/main/java/com/beatparty/beatpartyapp`  
JUnit test suite: `src/test/java/com/beatparty/beatpartyapp`  
Maven dependency file: `/pom.xml`  
Documentation: `/README.md`  
CI/CD build scripts: `/.travis.yml`  
  
**homepage-modules:** (merge to master upon completion)  
Front-end React and Typescript Functionality: `src/main/interface/src/`  
Front-end documentation: `/src/main/interface/README.md`  
  
#### Build and Run instructions
Build the SpringBoot project using Maven:  
`mvn compile`  
  
Run the JUnit test suite:  
`mvn compile  
mvn test`  
  
Run the SpringBoot application using the mvnw launcher:  
`./mvnw spring-boot:run`  
  
Run the React and Typescript application:  
`npm start`  
  
#### Database resources
This project uses an Azure SQL database to store information for each song.  
  Azure account: beatpartdev@hotmail.com (Contact rohinmeduri for password)  
  Resource group: Backend  
  Server name: beat-party-server.database.windows.net  
  Database name: BeatPartyDB  
  
### Version Control Guidelines
---
#### Cloning
Clone the git repository to your local filesystem using either one of the following.  
HTTPS: `git clone https://github.com/v-subbiah/beatparty.git`  
SSH: `git@github.com:v-subbiah/beatparty.git`  
  
#### Committing
Please commit all local changes to a branch. If you are currently do not have a branch you are working, please create a new branch as follows.  
Creating a new branch: `git branch <branch_name>`  
  
To make changes and/or commit on this branch, please checkout the branch as follows.  
Checking out a branch: `git checkout <branch_name>`  
  
⚠️ **Warning:** git will not allow you to checkout a branch if you have existing changes you have not stashed or commit on your current branch.  
  
#### Pushing
The repository will not allow you to push changes to your branch unless you set an upstream remote branch as follows.  
Setting an upstream branch: `git push --set-upstream https://github.com/v-subbiah/beatparty.git HEAD:<branch_name>`  
  
If there is no such upstream branch on remote yet, set the upstream branch with the intended branch name and GitHub will create the respective branch for you.  
  
Once you have set the upstream branch on remote, you may continue to push changes using the following.  
Pushing changes to an existing branch on remote: `git push`  
  
**Local Changes**: If you are working on certain modules that are yet to be commited to `master` alone, please maintain your branch on your local machine and push the branch to `remote` once you are ready to merge the branch into `master`. This will help reduce the number of branches that the remainder of the team needs to track.
  
**Remote Changes**: If you are working on modules that need attention of other members on, create an upstream branch on remote and continue to push your changes to the upstream branch on `remote`.  Please remind your teammates to pull the most recent commits to the branch from `remote` before beginning any work on the branch.  
  
⚠️ **Warning**: Do not push any code directly to the `origin/master` branch under any circumstances, as this **will result in breaking the build**. Please create a branch as described above and create a pull request when ready to merge the branch.
