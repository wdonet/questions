# NS Questions API

## Introduction
[Spring Data Rest](https://spring.io/guides/gs/accessing-data-rest/) uses the [HAL format](http://stateless.co/hal_specification.html) for JSON output. It is flexible and offers a convenient way to supply links adjacent to the data that is served. You can simply go to [/api](http://localhost:8080/api/) and use "_links" node to continue exploring on each request.
For example:
```
{
   "_links" : {
     "users" : {
       "href" : "http://localhost:8080/api/users"
     },
     "tags" : {
       "href" : "http://localhost:8080/api/tags{?page,size,sort}",
       "templated" : true
     }
   ...
   }
}
```

## Current URIs

All api URIs start with `/api` followed by one of the below.

### _GET_ http method
| URI | Description | Pagination | Sorting | + Params |
|:--- |:----------- |:----------:|:-------:|:------------ |
| `/users` | Show users | No | No | - |
| `/users/{id}` | Show user with id | No | No | `id` |
| `/tags` | Show all tags | Yes | Yes | - |
| `/tags/{id}` | Show tag by {id} | Yes | Yes | `id` |
| `/tags/{id}/user` | Show owner of tag with {id} (_not working_)| Yes | Yes | `id` |
| `/questions` | Show all questions | Yes | Yes | - |
| `/questions/{id}` | Show question by {id} | Yes | Yes | `id` |
| `/questions/{id}/answers` | Show all answers of question with {id} | Yes | Yes | `id` |
| `/questions/{id}/tags` | Show all tags of question with {id} | Yes | Yes | `id` |
| `/questions/{id}/user` | Show owner of question with {id} (_not working_)| No | No | `id` |
| `/questions/search` | Search questions using ?term={term} param.| Yes | Yes | `term` |
| `/answers` | Show all answers | Yes | Yes | - |
| `/answers/{id}` | Show answer by {id} | Yes | Yes | `id` |
| `/answers/{id}/question` | Show question of answer with {id} | Yes | Yes | `id` |
| `/answers/{id}/user` | Show user of answer with {id} (_not working_) | No | No | `id` |
| `/profile` | Show profile details (_not working_) | No | No | - |

### _POST_ http method
| URI | Description | Pagination | Sorting | Need + Token |
|:--- |:----------- |:----------:|:-------:|:------------ |
| `pending` | ... | No | No | No |

### _PUT_ http method
| URI | Description | Pagination | Sorting | Need + Token |
|:--- |:----------- |:----------:|:-------:|:------------ |
| `pending` | ... | No | No | No |

### _DELETE_ http method
| URI | Description | Pagination | Sorting | Need + Token |
|:--- |:----------- |:----------:|:-------:|:------------ |
| `pending` | ... | No | No | No |

### Notes:
1. **Pagination** uses 2 variables:
  + _page_ : Zero based index of number of page
  + _size_ : Total results by page
2. **Sorting** uses the name of field and default type is ASC, for DESC need to add the ".dir=desc" to the name of field. For field "name", it should be "name.dir=desc"
  + _sort_ : The name of field to use
  + _asc/desc_ : You can specify direction using a comma ',' after field name followed by `asc` or `desc` value.  Default is 'asc'.  For example `?sort=name,desc` => _Sorts by descending name_
    + Value can also be embedded like `?sort=user.firstName,desc`, where user is an embedded object with property 'firstName'
    + Multiple sorting by adding multiple 'sort' params like `?sort=firstName,asc&sort=lastName,desc`
3. **Searches** with multiple {term} params can be used as AND for more specific search.
