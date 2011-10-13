####1.0.11:

* Bug: newline inside `tag:\n{}`, [issues/20] (https://github.com/afelix/bemidea-bemhtml/issues/20).
* Annotated validation: [issues/14] (https://github.com/afelix/bemidea-bemhtml/issues/14), [issues/21] (https://github.com/afelix/bemidea-bemhtml/issues/21).

####1.0.10:

* BEMHTML line & block comments.
* Annotated validation: [issues/13] (https://github.com/afelix/bemidea-bemhtml/issues/13), [issues/15] (https://github.com/afelix/bemidea-bemhtml/issues/15), [issues/17] (https://github.com/afelix/bemidea-bemhtml/issues/17), [issues/18] (https://github.com/afelix/bemidea-bemhtml/issues/18).

####1.0.9:

* Bug: wrong EOF in case of unmatched JavaScript right curly brace.

####1.0.8:

* Plugin initialization refactored.

####1.0.7:

* Needless curly braces removed from prefix and suffix of injected JavaScript block.
* Annotated validation: unexpected character.

####1.0.6:

* Annotated validation.
* Bug: escaped chars in strings inside JavaScript expression.

####1.0.5:

* IDEA11 EAP family support.
* Simplified color scheme.
* Bug: incorrect `'x'+'y'` expression recognition.

####1.0.4:

* JavaScript injection hack replaced by better solution.
* Bug: incorrect `x ? y : z` expression recognition, [issues/8] (https://github.com/afelix/bemidea-bemhtml/issues/8).
* Bug: exception in case of unfinished JavaScript string literal.

####1.0.3:

* Temporary hack: hide file reload exception on plugin startup.
* Custom parser instead of JFlex. It was a pain to use JFlex 'look-ahead'.
* BEMJSON fix: correct recognition of properties' names.
* Some validation added.

####1.0.2:

* Basic structure view.

####1.0.1:

* Braces matching, [issues/2] (https://github.com/afelix/bemidea-bemhtml/issues/2).
* Some prefixes added to avoid embedded JavaScript warnings.
* Bug: Unresolved JavaScript array inside the template body, [issues/1] (https://github.com/afelix/bemidea-bemhtml/issues/1).

####1.0.0:

* Basic syntax highlighting.
