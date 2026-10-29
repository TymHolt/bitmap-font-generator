# Bitmap Font Generator
This tool can generate bitmap fonts from TrueType fonts installed on your system. Style and size
can be adjusted, and you can preview your generated font before exporting it. In addition to the
image exported as ```.png```, you will have a ```.xml``` file containing all metadata. Either start it without
arguments to run with a **GUI** or use command line arguments to run the generation instantly as **CLI**.

### GUI
![Screenshot](/img/screenshot.jpg?raw=true "Screenshot")

### CLI
>java -jar \<path-to-jar\> -out \<file-path\>

**All options:**
- ```-font (-f) <name>``` The font name
- ```-size (-s) <size>``` The font size
- ```-out -o <file-path>``` The output file
- ```-out-xml (-x) <file-path>``` Explicit path for the metadata export
- ```-style (-t) <plain/italic/bold>``` The font style
- ```-begin (-b) <code>``` First char to render
- ```-end (-e) <code>``` Last char to render
- ```-antialias (-a) <true/false>``` Enable anti-aliasing
- ```-invert-y (-y)<true/false>``` Invert Y axis of coordinates
- ```-uv <true/false>``` Export UV coordinates
- ```-force <true/false>``` If files should be overwritten without asking
- ```-help (-usage)``` Show usage information

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
This project uses **Maven** as build tool and targets **Java 21**. Run

> mvn clean package

to get an executable .jar file, which can also be used as a library for projects.