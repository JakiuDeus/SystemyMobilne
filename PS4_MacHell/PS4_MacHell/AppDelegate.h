//
//  AppDelegate.h
//  PS4_MacHell
//
//  Created by student on 07/11/2023.
//  Copyright © 2023 me.jorlowski. All rights reserved.
//

#import <UIKit/UIKit.h>
#import <CoreData/CoreData.h>

@interface AppDelegate : UIResponder <UIApplicationDelegate>

@property (strong, nonatomic) UIWindow *window;

@property (readonly, strong) NSPersistentContainer *persistentContainer;

- (void)saveContext;


@end

