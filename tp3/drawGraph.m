file = fopen('pts.txt','r');

line = fgetl(file);
arrayLength= [];
elementNumber = [];

while 1
    value = strsplit(line);
    elementNumber = [elementNumber, str2num(value{1})];
    arrayLength = [arrayLength , str2num(value{2})];
    line = fgets(file);
    if line == -1
        break;
    end
end

fclose(file);

plot(elementNumber, arrayLength);
