Narrative:

In order to constrain the view of the observation of a single entity
As a developer
I want to be able to specify the query view to be used by the corresponding EntityObserver's method

Scenario: entity observation is constrained accordingly

Given data:

[{
    v:1,
    e1:2,
    e2:{v:3, n:4},
    c1:{v:5, g1:{v:6, e1:7, e2:{v:8, n:9}}, g2:{v:10, e1:11, e2:{v:12, n:13}}},
    c2:{v:14, g1:{v:15, e1:16, e2:{v:17, n:18}}, g2:{v:19, e1:20, e2:{v:21, n:22}}}
},
{
    v:23,
    e1:24,
    e2:{v:25, n:26},
    c1:{v:27, g1:{v:28, e1:29, e2:{v:30, n:31}}, g2:{v:32, e1:33, e2:{v:34, n:35}}},
    c2:{v:36, g1:{v:37, e1:38, e2:{v:39, n:40}}, g2:{v:41, e1:42, e2:{v:43, n:44}}}
},
{
    v:45,
    e1:46,
    e2:{v:47, n:48},
    c1:{v:49, g1:{v:50, e1:51, e2:{v:52, n:53}}, g2:{v:54, e1:55, e2:{v:56, n:57}}},
    c2:{v:58, g1:{v:59, e1:60, e2:{v:61, n:62}}, g2:{v:63, e1:64, e2:{v:65, n:66}}}
}]

And I'm using the query view <query_view>
When I'm observing the parent with value <parent>
Then the observed value is <result>

Examples:
{transformer=MULTILINE}
| query_view                                            | parent | result |
| ONLY_PARENT                                           | 1      | {
                                                                     v:1,
                                                                     e1:null,
                                                                     e2:null,
                                                                     c1:null,
                                                                     c2:null
                                                                   } |
| ONLY_PARENT                                           | 45     | {
                                                                     v:45,
                                                                     e1:null,
                                                                     e2:null,
                                                                     c1:null,
                                                                     c2:null
                                                                   } |
| PARENT_EMBEDDED_1                                     | 23     | {
                                                                     v:23,
                                                                     e1:24,
                                                                     e2:null,
                                                                     c1:null,
                                                                     c2:null
                                                                   } |
| PARENT_EMBEDDED_2                                     | 23     | {
                                                                     v:23,
                                                                     e1:null,
                                                                     e2:{v:25, n:26},
                                                                     c1:null,
                                                                     c2:null
                                                                   } |
| PARENT_EMBEDDED_2_CHILD_1                             | 23     | {
                                                                     v:23,
                                                                     e1:null,
                                                                     e2:{v:25, n:26},
                                                                     c1:{v:27, g1:null, g2:null},
                                                                     c2:null
                                                                   } |
| PARENT_EMBEDDED_1_CHILD_2                             | 23     | {
                                                                     v:23,
                                                                     e1:24,
                                                                     e2:null,
                                                                     c1:null,
                                                                     c2:{v:36, g1:null, g2:null}
                                                                   } |
| PARENT_EMBEDDED_1_CHILD_1_AND_GRANDCHILD_2            | 23     | {
                                                                     v:23,
                                                                     e1:24,
                                                                     e2:null,
                                                                     c1:{v:27, g1:null, g2:{v:32, e1:null, e2:null}},
                                                                     c2:null
                                                                   } |
| PARENT_EMBEDDED_2_CHILD_2_AND_GRANDCHILD_1            | 23     | {
                                                                     v:23,
                                                                     e1:null,
                                                                     e2:{v:25, n:26},
                                                                     c1:null,
                                                                     c2:{v:36, g1:{v:37, e1:null, e2:null}, g2:null}
                                                                   } |
| PARENT_EMBEDDED_1_CHILD_1_AND_GRANDCHILD_2_EMBEDDED_1 | 23     | {
                                                                     v:23,
                                                                     e1:24,
                                                                     e2:null,
                                                                     c1:{v:27, g1:null, g2:{v:32, e1:33, e2:null}},
                                                                     c2:null
                                                                   } |
| PARENT_EMBEDDED_2_CHILD_2_AND_GRANDCHILD_1_EMBEDDED_2 | 23     | {
                                                                     v:23,
                                                                     e1:null,
                                                                     e2:{v:25, n:26},
                                                                     c1:null,
                                                                     c2:{v:36, g1:{v:37, e1:null, e2:{v:39, n:40}}, g2:null}
                                                                   } |
| PARENT_EMBEDDEDS_CHILD_2_AND_GRANDCHILD_1_EMBEDDEDS   | 23     | {
                                                                     v:23,
                                                                     e1:24,
                                                                     e2:{v:25, n:26},
                                                                     c1:null,
                                                                     c2:{v:36, g1:{v:37, e1:38, e2:{v:39, n:40}}, g2:null}
                                                                   } |

Scenario: default filters are applied

Given data:

[{
    v:1,
    c1:{v:2, g1:{v:3}, g2:{v:4}},
    c2:{v:5, g1:{v:6}, g2:{v:7}}
},
{
    v:8,
    c1:{v:9, g1:{v:10}, g2:{v:11}},
    c2:{v:12, g1:{v:13}, g2:{v:14}}
},
{
    v:15,
    c1:{v:16, g1:{v:17}, g2:{v:18}},
    c2:{v:19, g1:{v:20}, g2:{v:21}}
}]

And a parent-only filter that filters parents with value <parent> is being used
And I'm using the query view <query_view>
When I'm observing the parent with value <parent>
And I increment by 1 the values of all child1
And I increment by 1 the values of all child2.grandchild1
Then later the observed values were <result>

Examples:
{transformer=MULTILINE}
| query_view                      | parent | result |
| PARENT_CHILD_1_AND_GRANDCHILD_2 | 8      | [{
                                                v:8,
                                                c1:{v:9, g1:null, g2:{v:11}},
                                                c2:null
                                              },
                                              {
                                                v:8,
                                                c1:{v:10, g1:null, g2:{v:11}},
                                                c2:null
                                              }] |
| PARENT_CHILD_2_AND_GRANDCHILD_1 | 8      | [{
                                                v:8,
                                                c1:null,
                                                c2:{v:12, g1:{v:13}, g2:null}
                                              },
                                              {
                                                v:8,
                                                c1:null,
                                                c2:{v:12, g1:{v:14}, g2:null}
                                              }] |
| PARENT_CHILD_2_AND_GRANDCHILD_2 | 8      | [{
                                                v:8,
                                                c1:null,
                                                c2:{v:12, g1:null, g2:{v:14}}
                                              },
                                              {
                                                v:8,
                                                c1:null,
                                                c2:{v:12, g1:null, g2:{v:14}}
                                              }] |