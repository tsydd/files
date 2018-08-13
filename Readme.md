`KotlinFileSystem` emulates slow filesystem with high throughput.
It contains methods
- `getChildren(path)` - returns names of files inside dir
- `get(path)` - returns file or directory by path
- `createDirectory(path)` - creates directory, if parent directory exists
- `createFile(path, content)` - creates file, if parent directory exists
- `delete(path)` - removes file, if it is file or empty directory

`SlowFileSystem` is blocking wrapper for `KotlinFileSystem`.

`AsyncFileSystem` is asynchronous wrapper for `KotlinFileSystem`.

You should
- implement `CopyStrategy`, which copies all filesystem from source to target.
- implement `DeleteStrategy`, which recursively deletes all files from filesystem.
- strategy should work as fast as possible and use as few as possible threads.
