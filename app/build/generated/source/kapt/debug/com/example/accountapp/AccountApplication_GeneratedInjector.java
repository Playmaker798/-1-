package com.example.accountapp;

import dagger.hilt.InstallIn;
import dagger.hilt.codegen.OriginatingElement;
import dagger.hilt.components.SingletonComponent;
import dagger.hilt.internal.GeneratedEntryPoint;

@OriginatingElement(
    topLevelClass = AccountApplication.class
)
@GeneratedEntryPoint
@InstallIn(SingletonComponent.class)
public interface AccountApplication_GeneratedInjector {
  void injectAccountApplication(AccountApplication accountApplication);
}
