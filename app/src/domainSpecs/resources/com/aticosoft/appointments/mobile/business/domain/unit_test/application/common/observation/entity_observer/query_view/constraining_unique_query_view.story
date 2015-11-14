Narrative:

In order to constrain the view of the observation of a unique query
As a developer
I want to be able to specify the query view to be used by the corresponding EntityObserver's method

Scenario: individual children

Given data:

[{
    v:1,
    c1:{v:2, g1:3, g2:4},
    c2:{v:5, g1:6, g2:7}
},
{
    v:8,
    c1:{v:9, g1:10, g2:11},
    c2:{v:12, g1:13, g2:14}
},
{
    v:15,
    c1:{v:16, g1:17, g2:18},
    c2:{v:19, g1:20, g2:21}
}]

When I use the query view <query_view>
And I'm observing the parent with value <root>
Then the observed value is <result>

Examples:
{transformer=MULTILINE}
| query_view                      | parent | result |
| ONLY_PARENT                     | 1      | {
                                                p:1,
                                                c1:null,
                                                c2:null
                                             } |
| ONLY_PARENT                     | 15     | {
                                                p:15,
                                                c1:null,
                                                c2:null
                                             } |
| PARENT_CHILD_1                  | 8      | {
                                                v:8,
                                                c1:{v:9, g1:null, g2:null},
                                                c2:null
                                             } |
| PARENT_CHILD_2                  | 8      | {
                                                v:8,
                                                c1:null,
                                                c2:{v:12, g1:null, g2:null}
                                             } |
| PARENT_CHILD_1_AND_GRANDCHILD_2 | 8      | {
                                                v:8,
                                                c1:{v:9, g1:null, g2:11},
                                                c2:null
                                             } |
| PARENT_CHILD_2_AND_GRANDCHILD_1 | 8      | {
                                                v:8,
                                                c1:null,
                                                c2:{v:12, g1:13, g2:null}
                                             } |