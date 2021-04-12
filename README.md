## Beat Party ![](https://travis-ci.com/v-subbiah/beatparty.svg?token=MWpStGKXXEjsTeLgMJyz&branch=master)

A CSE 403: Software Engineering (2021 Spring group project)  



#### Version Control Guidelines

##### Cloning

Clone the git repository to your local filesystem using either one of the following.

HTTPS: `git clone https://github.com/v-subbiah/beatparty.git`

SSH: `git@github.com:v-subbiah/beatparty.git`  



##### Committing

Please commit all local changes to a branch. If you are currently do not have a branch you are working, please create a new branch as follows.

Creating a new branch: `git branch <branch_name>`

To make changes and/or commit on this branch, please checkout the branch as follows.

Checking out a branch: `git checkout <branch_name>`

⚠️ **Warning:** git will not allow you to checkout a branch if you have existing changes you have not stashed or commit on your current branch.  



##### Pushing

The repository will not allow you to push changes to your branch unless you set an upstream remote branch as follows.

Setting an upstream branch: `git push --set-upstream https://github.com/v-subbiah/beatparty.git HEAD:<branch_name>`

If there is no such upstream branch on remote yet, set the upstream branch with the intended branch name and GitHub will create the respective branch for you.

Once you have set the upstream branch on remote, you may continue to push changes using the following.

Pushing changes to an existing branch on remote: `git push`  



**Local Changes**: If you are working on certain modules that are yet to be commited to `master` alone, please maintain your branch on your local machine and push the branch to `remote` once you are ready to merge the branch into `master`. This will help reduce the number of branches that the remainder of the team needs to track.

**Remote Changes**: If you are working on modules that need attention of other members on, create an upstream branch on remote and continue to push your changes to the upstream branch on `remote`.  Please remind your teammates to pull the most recent commits to the branch from `remote` before beginning any work on the branch.  



⚠️ **Warning**: Do not push any code directly to the `origin/master` branch under any circumstances, as this **will result in breaking the build**. Please create a branch as described above and create a pull request when ready to merge the branch.











