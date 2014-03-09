# Point Location 6709 

A Java representation of [ISO 6709] geographic point location by coordinates. All classes are immutable and thread-safe. The code includes a parser that can parse all valid ISO 6709 representations. A formatter formats point locations to ISO 6709 "[human interface]" representations as well as "[string expressions]". Validity is enforced by JUnit tests. Maven is needed for a build.

[ISO 6709]: https://en.wikipedia.org/wiki/ISO_6709
[human interface]: https://en.wikipedia.org/wiki/ISO_6709#Representation_at_the_human_interface_.28Annex_D.29
[string expressions]: https://en.wikipedia.org/wiki/ISO_6709#String_expression_.28Annex_H.29