Narrative:

In order to filter the observation of a count query
As a developer
I want to be able to specify the filters to use when using the corresponding EntityObserver's method

Scenario: main scenario

!--Meta: @defaultObserverImplementation no

GivenStories: com/aticosoft/appointments/mobile/business/domain/unit_test/application/common/observation/entity_observer/filtering/given_stories/filtering_observation_of_count_query.scenario1.story

Given dummy step

Examples:
| filter                           | result                    |
| PRIME_PARENT                     | 0, 3, 4, 3, 3, 2, 4, 5, 4 |
| EVEN_PARENT                      | 0, 3, 4, 2, 4, 4, 5       |
| ODD_CHILD                        | 0, 3, 4, 3, 3, 3, 4, 4, 4 |
| ODD_CHILD_REMOVED                | 0, 3, 4                   |
| ODD_AND_DIVISIBLE_BY_THREE_CHILD | 0, 3, 4, 3, 3, 3, 4       |

!--Scenario: no filter with default EntityObserver implementation
!--
!--Meta: @defaultObserverImplementation yes
!--
!--GivenStories: com/aticosoft/appointments/mobile/business/domain/unit_test/application/common/observation/entity_observer/filtering/given_stories/filtering_observation_of_count_query.scenario1.story
!--
!--Given dummy step
!--
!--Examples:
!--| filter                           | result                    |
!--| NO_FILTER                        | 0, 3, 4, 3, 3, 2, 4, 5, 4 |