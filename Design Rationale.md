# Design Rationale

Collation API Translation

## Overview

The original C API provides locale-sensitive string collation through two parallel sets of functions: one for multibyte strings (strcoll/strxfrm) and another for wide characters (wcscoll/wcsxfrm). This approach was necessary in C to handle different character encodings and Unicode. In translating to Java, we were able to significantly simplify this design due to Java's unified String representation, which internally uses Unicode. Our Java implementation put these parallel functions into a single, cleaner API while maintaining the core functionality of both direct comparison and explicit transformation for multiple comparisons.

## Character Handling Simplification

One of the most significant improvements in the Java translation is by eliminating the parallel APIs needed in C. Where C requires separate functions strcoll/wcscoll for different character encodings, Java's String class already handles Unicode characters. This allowed us to provide a single compare() method that handles all character types correctly. Similarly, our createKey() method works uniformly with any valid String input. This simplification not only makes the API more straightforward to use but also eliminates potential errors that could arise from choosing the wrong function for a given string encoding.

## Key Design Decisions

**Overall Structure**  
The API is organized into three classes. The main Collation class serves as the primary entry point that provides static utility methods for string comparison and key generation. An immutable class CollationKey class comparison operations and the package-private CollationRules class handle the implementation details of locale-specific rules.

**String Comparison Design**  
The compare() method in the Collation class provides direct string comparison with locale sensitivity. It takes two strings and a locale as parameters, making the locale dependency explicit rather than implicit. This design choice improves thread safety and makes the API's behavior more predictable and testable. The method follows Java's comparison convention of returning negative, zero, or positive integers to indicate relative ordering.

**Immutability and Thread Safety**  
Both the Collation utility class and CollationKey instances are immutable. CollationKey maintains immutability by defensively copying its internal byte array and providing no mutator methods. This design choice ensures thread safety and allows CollationKey instances to be safely shared across threads or stored in collections.

**Locale Support**  
We moved away from C's global locale state (set through setlocale()) to explicit locale parameters in methods. The API defines supported locales clearly and provides constants for commonly used ones. The API explicitly defines supported locales through a combination of built-in constants (Locale.FRENCH) and custom constants (SPANISH). The isSupported() method allows clients to check locale support before use, following the principle of failing fast with clear error messages. This design makes it easy to add support for new locales in the future without breaking existing code.

**Error Handling**  
The API uses runtime exceptions with clear messages for invalid inputs. Parameter validation is performed early using Objects.requireNonNull with descriptive messages. This approach follows Java's fail-fast principle and helps developers identify issues during development rather than at runtime.

**Extensibility**  
While initially supporting French and Spanish locales, the design allows for easy addition of new locales. The CollationRules class encapsulates all locale-specific logic, making it straightforward to add support for additional locales without modifying the public API.  
