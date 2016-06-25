# z21-drive
A low level Java API for driving model trains with Fleischmann/Roco z21 DCC centre which takes care of wrapping your data into data that your z21 understands.
Meant for custom train controllers (cabs, control sticks...) and layout automation. Supports loco addresses up to 127.

In the current state it doesn't support some minor part of z21 network protocol. If you get information on unsupported features as output, create a new issue and I'll try to fix it. However in my testing I never got any errors like that so it should be ok.

For automation it currently lacks of LocoNet classes. If you feel like writing it, sure do it. I would be very glad if someone makes a PR for it.

Feel free to fork and make pull requests.

Now runs on gradle.
