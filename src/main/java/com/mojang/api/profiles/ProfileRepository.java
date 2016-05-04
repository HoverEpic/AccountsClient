package com.mojang.api.profiles;

public interface ProfileRepository
{

    Profile[] findProfilesByCriteria(ProfileCriteria... var1);
}
