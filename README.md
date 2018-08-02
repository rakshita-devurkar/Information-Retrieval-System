# information-retrieval-system

This application retrieves a set of relevant documents for the given query in the following steps: 

1. A small collection of movie reviews (html files) is read and a dictionary and a posting list is created.
2. Each record of the dictionary contains the term, the number of documents containing the term and the pointer to the posting file for the term
3. Each record in a posting list contains a Document ID.
4. A document table is created that contains the following - File name, Title of the movie, Reviewer, Snippet, Rate (P- Positive, N-Negative)
5. Query processing - The query is read and processed to return the results.

Example query : 

Query : AND mystery AND NOT horror

This results in a list of all the mystery movies that were not horro movies along with the snippet, reviewer and rate.
