# Android HttpAutoCompleteTextView

HttpAutoCompleteTextView is a library that extends Android's AutoCompleteTextView by adding ability to populate results from a web service.
- The library - httpautocompletetextview - includes all the functionality along with a couple of interfaces to make it generic.
- Sample app demonstrates how to use the library to show auto complete values for stock search using YUI.

### Version
1.0

### OS
Android

### Usage
* Implement ISearchConstraint to use your own webservice and result parsing.
* The parsed data should be converted to a List of ISearchResult
* Add DelayAutoCompleteTextView to your Activity or Fragment
* Refer to sample app to see usage in more detail