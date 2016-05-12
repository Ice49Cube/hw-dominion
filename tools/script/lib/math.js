
Math.radians = function(degrees) {
  return degrees * Math.PI / 180;
}; 
Math.degrees = function(radians) {
  return radians * 180 / Math.PI;
};
function Point(x, y) {
	this.x = x;
	this.y = y;
}
Point.prototype.subtract = function(other) { return new Point(this.x - other.x, this.y - other.y); };
Point.prototype.angle = function(other) {
    var delta = other.subtract(this);
    return 180 + Math.atan2(-delta.x, delta.y) * (180 / Math.PI);
};
Point.prototype.rotate = function(center, degrees) {
    var radians  = Math.radians(degrees * -1);
	var cos = Math.cos(radians);
	var sin = Math.sin(radians);
	var delta = this.subtract(center);
	return new Point((cos * delta.x + sin * delta.y) + center.x, (cos * delta.y - sin * delta.x) + center.y);
};
