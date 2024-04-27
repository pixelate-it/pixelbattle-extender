# Pixel Battle canvas extender
It is this script that extend (or creates) the canvas for Pixel Battle  
The script will give you a `canvas_*.json` file which needs to be imported using [MongoDB Compass](https://www.mongodb.com/products/tools/compass)

## Usage
1. Install dependencies with `npm i`
2. Fill out `.env` using the instructions below
3. Save the changes, and start script with `npm run extend` command

## Recommendations
If you use a center position, we recommend specifying the first 4 values divisible by only 2 in `.env`

## `.env` form
You can see example in the `.env.expamle` file

`NEW_CANVAS_X` - desired size **x** coordinates  
`NEW_CANVAS_Y` - desired size **y** coordinates  
`OLD_CANVAS_X` - old size **x** coordinates  
`OLD_CANVAS_Y` - old size **y** coordinates  
`POSITION` - position of the old canvas on the new one (see below)  
`CLR` - fill color for new areas (you can specify **RANDOM** for a random color)  
`PIXELS_FILE` - path to the file with the old canvas  
`BENCHMARK` - benchmark status (**TRUE** or **FALSE**)  
`ALGORITHM` - algorithm by which the extension works (see below)  
`SAVE_TO` - path to the directory where the canvases will be saved  

## Positions
**1** - top left  
**2** - top right  
**3** - bottom left  
**4** - bottom right  
**5** - center  

## Algorithms
**0** - a slow algorithm that works by constructing an array through brute force and finding it in an already existing canvas  