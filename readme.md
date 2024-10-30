# Collation API

A Java library providing locale-sensitive string comparison and sorting capabilities that currently support for French and Spanish locales. This is a Java translation of the GNU C Library Collation Functions.

## Source
This is a Java translation of the [GNU C Library Collation Functions](https://www.gnu.org/software/libc/manual/html_node/Collation-Functions.html):
```
https://www.gnu.org/software/libc/manual/html_node/Collation-Functions.html
```

## Project Structure
```
Collation/
├── src/
│   ├── collation/              # API implementation
│   │   ├── Collation.java
│   │   ├── CollationKey.java
│   │   ├── CollationRules.java
│   │   └── package-info.java
│   └── example/                # Usage examples
│       └── CollationSample.java
├── docs/                       # Generated API documentation
└── out/                        # Compiled classes
```

## Building the Project

### Generate API Documentation
```bash
# Generate Javadoc in docs directory
javadoc -d docs src/collation/*.java
```

### Compile the API
```bash
# Compile collation package
javac -d out src/collation/*.java
```

### Compile and Run Examples
```bash
# Compile the sample code
javac -cp out -d out/example src/example/CollationSample.java

# Run the sample program
java -cp "out:out/example" CollationSample
```

## Documentation
After generating the documentation, open `docs/index.html` in a web browser to view the complete API documentation.

## Supported Locales
- French (Locale.FRENCH)
- Spanish (Collation.SPANISH)
