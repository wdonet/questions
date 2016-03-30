# Contribution guidelines
We'd love for you to contribute to our source code and to make NS Questions better. Here are the guidelines we would like you to follow:

## Issues and bugs

If you find a bug in the source code or a mistake in the documentation, you can help us by submitting an issue to the [repository](https://github.com/Nearsoft/training). Even better you can submit a Pull Request with a fix.

## Feature request

You can request a new feature by submitting an issue to the [repository](https://github.com/Nearsoft/questions).

### Submitting a feature request

Please provide the following information.

- Explanation of the feature requested - What's the main idea of the feature and why do you think it would be useful to have it.
- Application example - Please write the steps you would like to do in order to use the feature requested.
- Suggestion - If you know any tool or how to implement this feature don't hesitate about writing it.

## Submission Guidelines

### Submitting an issue

Before you submit your issue search the archive, maybe your question was already answered.

If your issue appears to be a bug and it hasn't been reported, open a new issue.

- **Overview of the Issue** - Please describe briefly what is this bug about
- **Platform** - Which browser and operating system you were using when this issue came up?
- **Steps to Reproduce the Error** - Provide an unambiguous set of steps.
- **Expected result ** - Describe what is the expected behaviour
- **Suggest a Fix** - Do you know any tool or any probable cause for the bug?

### Submitting a Pull Request

If you want to contribute creating a pull request to merge your code, please follow :

- Make your changes in a new git branch (using your fork):
```
git checkout -b my-fix-branch
```
- Create your patch
- Commit your changes using a descriptive commit message. (Please check the next section to see how to write a good commit message)
- Merge the code from dev into your fix branch (watering)
- In GitHub, send a pull request to `training:dev`
- If someone suggest changes (and the changes make sense) then:
  - Make the required updates.
  - Commit your changes to your branch (e.g. my-fix-branch).
  - Push the changes to your GitHub repository (this will update your Pull Request).
- If the PR gets too outdated we may ask you to rebase.

### How is a descriptive message for referencing an issue?

It is strongly encouraged to follow the below format for any commit in order to track changes better.

 - Every time you had a fix for any issue at Github you must add a descriptive commit message
 - Format: "[word] #N message", where
    - N is the issue number at github
    - 'word' is one of the listed below
        - close
        - closes
        - closed
        - fix
        - fixes
        - fixed
        - resolve
        - resolves
        - resolved
    - and the message is the description of what was done.

In that way Github can add links to the commit's related issues.

If you still have any question about how to contribute, please feel free to poke some of [us](https://github.com/Nearsoft/questions/graphs/contributors) to ask your questions. We need people to build this faster. Either you work in back end or front end side you will be really helpful on this.

That's it! Thank you for your contribution!