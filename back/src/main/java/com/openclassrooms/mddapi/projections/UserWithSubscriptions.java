package com.openclassrooms.mddapi.projections;


import com.openclassrooms.mddapi.model.Topic;

import java.util.List;

public interface UserWithSubscriptions {
    String getUsername();
    String getEmail();
    List<Topic> getSubscriptions();
}
