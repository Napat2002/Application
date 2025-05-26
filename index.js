function findClosest(arr, target) {
  let a = 0, b = arr.length - 1, best = arr[0];
  while (a <= b) {
    let m = (a + b) >> 1, val = arr[m];
    if (Math.abs(val - target) < Math.abs(best - target)) best = val;
    if (val < target) a = m + 1;
    else if (val > target) b = m - 1;
    else return val;
  }
  return best;
}

const arr = Array.from({ length: 1e6 + 1 }, (_, i) => i);
const result = findClosest(arr, 900000);
console.log("ใกล้เคียง:", result);
