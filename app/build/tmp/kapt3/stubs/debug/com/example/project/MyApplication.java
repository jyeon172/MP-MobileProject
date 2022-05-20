package com.example.project;

import java.lang.System;

@kotlin.Metadata(mv = {1, 5, 1}, k = 1, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\u0018\u0000 \u00052\u00020\u0001:\u0001\u0005B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H\u0016\u00a8\u0006\u0006"}, d2 = {"Lcom/example/project/MyApplication;", "Landroidx/multidex/MultiDexApplication;", "()V", "onCreate", "", "Companion", "app_debug"})
public final class MyApplication extends androidx.multidex.MultiDexApplication {
    @org.jetbrains.annotations.NotNull()
    public static final com.example.project.MyApplication.Companion Companion = null;
    public static com.google.firebase.auth.FirebaseAuth auth;
    public static com.example.project.PreferenceUtil prefs;
    @org.jetbrains.annotations.Nullable()
    private static java.lang.String email;
    public static com.google.firebase.firestore.FirebaseFirestore db;
    public static com.google.firebase.storage.FirebaseStorage storage;
    
    public MyApplication() {
        super();
    }
    
    @java.lang.Override()
    public void onCreate() {
    }
    
    @kotlin.Metadata(mv = {1, 5, 1}, k = 1, d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0006\u0010!\u001a\u00020\"R\u001a\u0010\u0003\u001a\u00020\u0004X\u0086.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001a\u0010\t\u001a\u00020\nX\u0086.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u001c\u0010\u000f\u001a\u0004\u0018\u00010\u0010X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014R\u001a\u0010\u0015\u001a\u00020\u0016X\u0086.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0017\u0010\u0018\"\u0004\b\u0019\u0010\u001aR\u001a\u0010\u001b\u001a\u00020\u001cX\u0086.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001d\u0010\u001e\"\u0004\b\u001f\u0010 \u00a8\u0006#"}, d2 = {"Lcom/example/project/MyApplication$Companion;", "", "()V", "auth", "Lcom/google/firebase/auth/FirebaseAuth;", "getAuth", "()Lcom/google/firebase/auth/FirebaseAuth;", "setAuth", "(Lcom/google/firebase/auth/FirebaseAuth;)V", "db", "Lcom/google/firebase/firestore/FirebaseFirestore;", "getDb", "()Lcom/google/firebase/firestore/FirebaseFirestore;", "setDb", "(Lcom/google/firebase/firestore/FirebaseFirestore;)V", "email", "", "getEmail", "()Ljava/lang/String;", "setEmail", "(Ljava/lang/String;)V", "prefs", "Lcom/example/project/PreferenceUtil;", "getPrefs", "()Lcom/example/project/PreferenceUtil;", "setPrefs", "(Lcom/example/project/PreferenceUtil;)V", "storage", "Lcom/google/firebase/storage/FirebaseStorage;", "getStorage", "()Lcom/google/firebase/storage/FirebaseStorage;", "setStorage", "(Lcom/google/firebase/storage/FirebaseStorage;)V", "checkAuth", "", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.google.firebase.auth.FirebaseAuth getAuth() {
            return null;
        }
        
        public final void setAuth(@org.jetbrains.annotations.NotNull()
        com.google.firebase.auth.FirebaseAuth p0) {
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.example.project.PreferenceUtil getPrefs() {
            return null;
        }
        
        public final void setPrefs(@org.jetbrains.annotations.NotNull()
        com.example.project.PreferenceUtil p0) {
        }
        
        @org.jetbrains.annotations.Nullable()
        public final java.lang.String getEmail() {
            return null;
        }
        
        public final void setEmail(@org.jetbrains.annotations.Nullable()
        java.lang.String p0) {
        }
        
        public final boolean checkAuth() {
            return false;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.google.firebase.firestore.FirebaseFirestore getDb() {
            return null;
        }
        
        public final void setDb(@org.jetbrains.annotations.NotNull()
        com.google.firebase.firestore.FirebaseFirestore p0) {
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.google.firebase.storage.FirebaseStorage getStorage() {
            return null;
        }
        
        public final void setStorage(@org.jetbrains.annotations.NotNull()
        com.google.firebase.storage.FirebaseStorage p0) {
        }
    }
}