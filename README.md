# Ayoda                                                                                      
                                                                                             
  An Android platform connecting NGOs with people in need. Users can post requests for help,   
  browse NGO listings, and connect with organizations directly.                              
                                                                                               
  ## Features                                                                                  
   
  - Firebase authentication (Sign In / Sign Up)                                                
  - Post and browse help requests                           
  - NGO directory with detailed view
  - Splash screen with smooth onboarding                                                       
  - RecyclerView-based request feed with custom adapter
                                                                                               
  ## Tech Stack                                                                                
   
  - **Language:** Kotlin                                                                       
  - **Platform:** Android                                   
  - **Backend:** Firebase (Auth + Firestore)                                                   
  - **UI:** XML Layouts, RecyclerView                       
                                              
  ## Getting Started                      
                                                                                               
  ```bash                                                                                      
  git clone https://github.com/Lakshrajj/Ayoda.git                                             
                                                                                               
  1. Open in Android Studio                                 
  2. Add your google-services.json to app/
  3. Build & run on emulator or device (min SDK: check build.gradle)
                                              
  Project Structure                       
                                                                                               
  app/src/main/java/com/luxx/ayoda/                                                            
  ├── MainActivity.kt        # Home feed                                                       
  ├── NgoView.kt             # NGO detail screen                                               
  ├── PostActivity.kt        # Create new request           
  ├── RequestCard.kt         # Request item UI                                                 
  ├── SignInActivity.kt      # Auth screen    
  ├── SplashScreen.kt        # Launch screen                                                   
  ├── requestAdapter.kt      # RecyclerView adapter         
  └── requestData.kt         # Data model      
