# z21-drive [![Build Status](https://travis-ci.org/grizeldi/z21-drive.svg?branch=main)](https://travis-ci.org/grizeldi/z21-drive)
A low level Java API for driving model trains with Fleischmann/Roco z21 DCC centre
which takes care of wrapping your data into data that your z21 understands.
Meant for custom train controllers (cabs, control sticks...) and layout automation.
Supports loco addresses up to 127. Removing the limit is in progress.

## Current state
In the current state it doesn't support some minor part of z21 network protocol.
If you get information on unsupported features as output, create a new issue and
I'll try to fix it. However in my testing I never got any errors like that so it should be ok.

For automation it currently lacks of LocoNet classes. If you feel like writing it, sure do it.
I would be very glad if someone makes a PR for it.


## Contributing and installation
### Contributing
Feel free to fork and make pull requests. There is also a github wiki, which doesn't contain
very much useful information right now. It contains the getting started guide, so you
can start there.

The library now runs on gradle, but it's not yet accessible in any public repository. Automatic
publication of new releases is work in progress.

### Installation
Download the latest release from github releases, add the jar to your classpath/project,
and you are good to go. Releases are at the moment not published very often so you might
want to build from source. In that case you just have to execute gradle build.

## Projects using this library
 * My piThrottle cab controller
 * LittleYoda's ESU [decoder programmer](https://github.com/littleyoda/ly-Z21-Tool)
