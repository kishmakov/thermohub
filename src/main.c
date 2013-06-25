/* 
 * File:   main.c
 * Author: menato
 *
 * Created on September 20, 2012, 8:58 PM
 */

#include <stdio.h>
#include <stdlib.h>
#include <math.h>

#define sqr(a) ((a)*(a))
#define min(a, b) ((a) < (b) ? (a) : (b))

#define maxn 100

const double eps = 1e-6;
const double inf = 1e16;

const double beginWall = 0;
const double endWall = 10;

int n;
double position[maxn];
double velocity[maxn];
double mass[maxn];

double collisionTime[maxn][maxn];
double beginWallColisionTime[maxn];
double endWallColisionTime[maxn];

double calculateCollisionTime(int i, int j) {
    double vi = velocity[i];
    double vj = velocity[j];
    
    double dist = position[j] - position[i];
    double coming = vi - vj; 
    
    if (coming < eps) {
        return inf;
    } else {
        return dist / coming;
    }
}

void collide(int i, int j) {
    double vi = velocity[i];
    double vj = velocity[j];
    
    double mi = mass[i];
    double mj = mass[j];    
    
    double x = (mi * vi + mj * vj) / (mi + mj);
    
    //printf("b: vi = %g vj = %g\n", vi, vj);
    
    velocity[i] = 2 * x - vi;
    velocity[j] = 2 * x - vj;
    
    //printf("a: vi = %g vj = %g\n\n", velocity[i], velocity[j]);
}



int main(int argc, char ** argv) {
    
    int i, j;
    double t, T, t1, t2;
    double dt;
    double t0, energy;
    double momentum, pressure;
    double velsum;
    FILE * out;
    
    if (argc < 5) {
        printf("Usage: program <n> <time> <randseed> <output>\n");
        return 1;
    }
    
    n = atoi(argv[1]);
    T = atoi(argv[2]);
    srand(atoi(argv[3]));
    
    energy = 0;
    momentum = 0;
    velsum = 0;
    t0 = 1;    
    for (i = 0; i < n; i++) {
        mass[i] = (rand() % 4) + 1;
        velocity[i] = (rand() % 5) - 2;
        position[i] = 1 + (rand() % 10);
        velsum += fabs(velocity[i]);        
        momentum += mass[i] * fabs(velocity[i]);
        energy += mass[i] * sqr(velocity[i]);
        t0 += position[i];
    }
    
    if (velsum < eps) {
        return 0;
    }
    
    t1 = 0;
    for (i = 0; i < n; i++) {
        t1 += position[i];
        position[i] = 10.0 * t1 / t0; 
    }
    
    pressure = 0;
    t = 0;
    
    while (t < T - eps) {
        
        // time to closest event
        
        dt = T - t;// time to closest event
        
        for (i = 0; i < n; i++) {
            t1 = fabs(velocity[i]);
            if (velocity[i] > 0) {
                beginWallColisionTime[i] = inf;
                endWallColisionTime[i] = (endWall - position[i]) / t1;
            } else {
                endWallColisionTime[i] = inf;
                beginWallColisionTime[i] = (position[i] - beginWall) / t1;
            }
            
            dt = min(dt, beginWallColisionTime[i]);
            dt = min(dt, endWallColisionTime[i]);
        }
        
        for (i = 0; i < n - 1; i++) {
            j = i+1;
            collisionTime[i][j] = calculateCollisionTime(i, j);
            dt = min(dt, collisionTime[i][j]);
        }
        
        // time step
        
        for(i = 0; i < n; i++) {
            position[i] += velocity[i] * dt;
        }
        
        // collisions processing
        
        for (i = 0; i < n; i++) {
            if (fabs(dt - beginWallColisionTime[i]) < eps) {
                velocity[i] = - velocity[i];
                pressure += fabs(velocity[i]) * mass[i];
            }
            if (fabs(dt - endWallColisionTime[i]) < eps) {
                velocity[i] = - velocity[i];
                pressure += fabs(velocity[i]) * mass[i];
            }            
        }
        
        for (i = 0; i < n - 1; i++) {
            j = i + 1;
            if (fabs(dt - collisionTime[i][j]) < eps) {
                collide(i, j);
            }            
        }
            
        // time shift
        
        t += dt;
    }
    
    // recompute energy and momentum
    
    energy = 0;
    momentum = 0;
    for (i = 0; i < n; i++) {
        momentum += mass[i] * fabs(velocity[i]);
        energy += mass[i] * sqr(velocity[i]);
    }
    
    // output
    
    out = fopen(argv[4], "at");
    
    fprintf(out, "%15.2lfP%15.2lfV%15.2lfM%15.2lfE%15.2lf%15.2lf%15.2lf\n", pressure, velsum, momentum, energy, pressure / velsum, pressure / momentum, pressure / energy);
    
    fclose(out);    

    return 0;
}