# Pixel Battle canvas extender
It is this script that extend (or creates) the canvas for Pixel Battle  
The script will give you a `canvas_*.json` file which needs to be imported using [MongoDB Compass](https://www.mongodb.com/products/tools/compass)

## Usage
1. Install dependencies with `npm i`
2. Fill out `.env` using the instructions below
3. Save the changes, and start script with `npm run extend` command

## Recommendations
**This utility is under development**, we recommend specifying in `.env` the first 4 values ​​only divisible by 2

## `.env` form
You can see example in the `.env` file

`NEW_CANVAS_X` - desired size **x** coordinates  
`NEW_CANVAS_Y` - desired size **y** coordinates  
`OLD_CANVAS_X` - old size **x** coordinates  
`OLD_CANVAS_Y` - old size **y** coordinates  
`POSITION` - position of the old canvas on the new one (see below)  
`CLR` - fill color for new areas (you can specify RANDOM for a random color)  
`PIXELS_FILE` - path to the file with the old canvas  

## Positions
**1** - top left  
**2** - top right  
**3** - bottom left  
**4** - bottom right  
**5** - center  