# Google Hash Code 2017
Online Qualification submission for [Google Hash Code](https://hashcode.withgoogle.com/) 2017.

**Team members:** Marcel Eschmann, Cedric Seger and Simone Stefani <br>
**Hub:** Royal Institute of Technology (KTH) - Stockholm

---

### Introduction
Have you ever wondered what happens behind the scenes when you watch a YouTube video? As more and more people watch online videos (and as the size of these videos increases), it is critical that video-serving infrastructure is optimized to handle requests reliably and quickly. This typically involves putting in place cache servers, which store copies of popular videos. When a user
request for a particular video arrives, it can be handled by a cache server close to the user, rather than by a
remote data center thousands of kilometers away.
But how should you decide which videos to put in which cache servers?

### Task
Given a description of cache servers, network endpoints and videos, along with predicted requests for
individual videos, decide which videos to put in which cache server in order to minimize the average
waiting time for all requests.


#### Example of video requests allocation on cache servers
Input Data Set: "Me at the zoo"
![Caches hit image](https://github.com/SimoneStefani/google-hash-code-2017/blob/master/img/hashcode.png)
