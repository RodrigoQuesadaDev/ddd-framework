Narrative:

In order to constrain the view of the observation of a list query
As a developer
I want to be able to specify the query view to be used by the corresponding EntityObserver's method

Scenario: entities observation is constrained accordingly

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

And I'm using the query view <query_view>
When I'm observing the parents with odd value
Then the observed value is <result>

Examples:
{transformer=MULTILINE}
| query_view                      | result |
| ONLY_PARENT                     | [{
                                       v:1,
                                       c1:null,
                                       c2:null
                                     },
                                     {
                                       v:15,
                                       c1:null,
                                       c2:null
                                     }] |
| PARENT_CHILD_1                  | [{
                                       v:1,
                                       c1:{v:2, g1:null, g2:null},
                                       c2:null
                                     },
                                     {
                                       v:15,
                                       c1:{v:16, g1:null, g2:null},
                                       c2:null
                                     }] |
| PARENT_CHILD_2                  | [{
                                       v:1,
                                       c1:null,
                                       c2:{v:5, g1:null, g2:null}
                                     },
                                     {
                                       v:15,
                                       c1:null,
                                       c2:{v:19, g1:null, g2:null}
                                     }] |
| PARENT_CHILD_1_AND_GRANDCHILD_2 | [{
                                       v:1,
                                       c1:{v:2, g1:null, g2:4},
                                       c2:null
                                     },
                                     {
                                       v:15,
                                       c1:{v:16, g1:null, g2:18},
                                       c2:null
                                     }] |
| PARENT_CHILD_2_AND_GRANDCHILD_1 | [{
                                       v:1,
                                       c1:null,
                                       c2:{v:5, g1:6, g2:null}
                                     },
                                     {
                                       v:15,
                                       c1:null,
                                       c2:{v:19, g1:20, g2:null}
                                     }] |

Scenario: default filters are applied

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

And an odd-parent filter that only filters parents is being used for the observation
And I'm using the query view <query_view>
When I'm observing the parents with odd value
And I increment by 1 the values of all child1
And I increment by 1 the values of all child2.grandchild1
Then later the observed values were <result>

Examples:
{transformer=MULTILINE}
| query_view                      | result |
| PARENT_CHILD_1_AND_GRANDCHILD_2 | [[{
                                       v:1,
                                       c1:{v:2, g1:null, g2:4},
                                       c2:null
                                     },
                                     {
                                       v:15,
                                       c1:{v:16, g1:null, g2:18},
                                       c2:null
                                     }],
                                     [{
                                       v:1,
                                       c1:{v:3, g1:null, g2:4},
                                       c2:null
                                     },
                                     {
                                       v:15,
                                       c1:{v:17, g1:null, g2:18},
                                       c2:null
                                     }]] |
| PARENT_CHILD_2_AND_GRANDCHILD_1 | [[{
                                       v:1,
                                       c1:null,
                                       c2:{v:5, g1:6, g2:null}
                                     },
                                     {
                                       v:15,
                                       c1:null,
                                       c2:{v:19, g1:20, g2:null}
                                     }],
                                     [{
                                       v:1,
                                       c1:null,
                                       c2:{v:5, g1:7, g2:null}
                                     },
                                     {
                                       v:15,
                                       c1:null,
                                       c2:{v:19, g1:21, g2:null}
                                     }]] |
| PARENT_CHILD_2_AND_GRANDCHILD_2 | [[{
                                       v:1,
                                       c1:null,
                                       c2:{v:5, g1:null, g2:7}
                                     },
                                     {
                                       v:15,
                                       c1:null,
                                       c2:{v:19, g1:null, g2:21}
                                     }],
                                     [{
                                       v:1,
                                       c1:null,
                                       c2:{v:5, g1:null, g2:7}
                                     },
                                     {
                                       v:15,
                                       c1:null,
                                       c2:{v:19, g1:null, g2:21}
                                     }]] |