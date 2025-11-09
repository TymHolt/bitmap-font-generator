# Bitmap Font Generator
This tool can generate bitmap fonts from TrueType fonts installed on your system. Style and size
can be adjusted, and you can preview your generated font before exporting it. In addition to the
image exported as .png, you will have a .txt file containing all characters coordinates. Each line
of this metadata contains the following information: \[ASCII\] \[x\] \[y\] \[height\] \[width\]

### Build
To build this application yourself, you will need
[smplmake v0.0.1](https://github.com/TymHolt/smpl-make/releases). Run

> smplmake

to get an executable .jar file.