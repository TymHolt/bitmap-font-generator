# Bitmap Font Generator
This tool can generate bitmap fonts from TrueType fonts installed on your system. Style and size
can be adjusted, and you can preview your generated font before exporting it. In addition to the
image exported as .png, you will have a .xml file containing all metadata. Either start it without
arguments to run with a GUI or use command line arguments to run the generation instantly.

### GUI
![Screenshot](/img/screenshot.jpg?raw=true "Screenshot")

### CLI
>java -jar \<path-to-jar\> -font \<name\> -size \<size\> -out \<file-path\>

**All options:**
- font (-f) <name> - *The font name*
- size (-si) <size> - *The font size*
- out (-o) <file-path> - *The output file*
- style (-st) <plain/italic/bold> - *The font style*
- count (-c) <count> - *Amount of chars to render*
- antialias (-aa) <true/false> - *Enable anti-aliasing*

### XML Metadata

```xml
<font leading="0">
    <glyph id="0" x="0" y="0" width="3" height="13"/>
    <glyph id="1" x="10" y="0" width="3" height="13"/>
    <glyph id="2" x="20" y="0" width="3" height="13"/>
    <glyph id="3" x="30" y="0" width="3" height="13"/>
    <glyph id="4" x="40" y="0" width="3" height="13"/>
    <glyph id="5" x="50" y="0" width="3" height="13"/>
    <glyph id="6" x="60" y="0" width="3" height="13"/>
    <glyph id="7" x="70" y="0" width="3" height="13"/>
    <glyph id="8" x="80" y="0" width="3" height="13"/>
    <glyph id="9" x="90" y="0" width="3" height="13"/>
    <...>
</font>
```


### Build
To build this application yourself, you will need
[smplmake v0.0.1](https://github.com/TymHolt/smpl-make/releases). Run

> smplmake

to get an executable .jar file, which can also be used as a library for projects.