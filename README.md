# CleanUpStupidMojangShit

A spigot plugin to remove broken FilteredText NBT data from signs.

To use, just install and walk near (within 8 blocks radius of) any signs that are broken. They will automatically fix themselves.

Make sure you're using the latest PaperMC version before using, else more bad data may be created.

## Explanation

**What is `FilteredText`?** A while ago, Mojang added some unused code for filtering swear words from the game. It's supposed to do this by creating a `FilteredText` copy of each sign and book, and attaching this to NBT data. When the feature is not used, `FilteredText` never gets generated.

Starting 2022 January 3, PaperMC had a change that mistakenly (?) wrote sign data to `FilteredText`. For whatever reason, some of these `FilteredText` fields ended up as blank or missing formatting. This only ever occured if a sign's contents were written through the PaperMC API. I think this change has since been reverted.

On 2022 April 28, Mojang seems to have pushed out a change to everyone's account data that forces their client to show the `FilteredText` version of signs if it is available. Why? Nobody knows &mdash; it doesn't even work properly! But it caused everyone to see screwed up signs all at once.

---

<sup>(?):</sup> So I don't think a vanilla server would generate this data at all. In the original Jan 3 issue thread, it was claimed that someone was seeing blank signs due to a *lack* of `FilteredText`, but as far as I know this is not how it is coded &mdash; it will fall back to `Text` if a filtered version isn't available, and Mojang code is specifically written to remove `FilteredText` if it exactly the same as `Text`. Also, it doesn't appear to be possible to configure chat filtering from Xbox privacy settings, despite what someone said. If you have any further insight, create a pull request to edit this `README.md` to correct anything I might have gotten wrong.

## License

Licensed under GPLv3

Copyright (c) 2021 lordpipe

### Contributing

By making a contribution to this repository, I certify that:

* (a) The contribution was created in whole or in part by me and I have the right to submit it under the open source license indicated in the file; or
* (b) The contribution is based upon previous work that, to the best of my knowledge, is covered under an appropriate open source license and I have the right under that license to submit that work with modifications, whether created in whole or in part by me, under the same open source license (unless I am permitted to submit under a different license), as indicated in the file; or
* (c) The contribution was provided directly to me by some other person who certified (a), (b) or (c) and I have not modified it.
* I understand and agree that this project and the contribution are public and that a record of the contribution (including all personal information I submit with it, including my sign-off) is maintained indefinitely and may be redistributed consistent with this project or the open source license(s) involved.
