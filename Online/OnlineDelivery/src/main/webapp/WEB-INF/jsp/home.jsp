<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Online Delivery</title>
    <!-- Include Bootstrap CSS -->
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <!-- Include Custom CSS -->
    <link rel="stylesheet" href="styles.css">
    <style>
        /* Custom CSS for improved color formatting */
        body {
            background-color: #f8f9fa; /* Light gray background */
        }
        .card {
            background-color: #ffffff; /* White card background */
            border: 1px solid #e0e0e0; /* Light gray border */
        }
        .card-header {
            background-color: #8F3A65; /* Primary red background */
            color: #ffffff; /* White text color */
        }
        .btn-primary {
            background-color: #8F3A65 !important; /* Primary red button color */
            border-color: #8F3A65 !important;
        }
        .form-group {
            border: 1px solid #808080; /* Secondary gray border for form elements */
            padding: 10px;
            margin-bottom: 15px;
        }
        .form-control {
            background-color: #ffffff; /* White background for form inputs */
        }
    </style>
</head>
<body>
    <div class="container mt-5">
        <div class="card">
            <div class="card-header">
                <h2 class="text-center mb-0">ONLINE DELIVERY</h2>
            </div>
            <div class="card-body">
                <form>
                    <div class="form-row">
                        <div class="form-group col-md-6">
                            <label for="name">Name</label>
                            <input type="text" class="form-control" id="name" placeholder="Enter name">
                        </div>
                        <div class="form-group col-md-6">
                            <label for="email">Email</label>
                            <input type="email" class="form-control" id="email" placeholder="Enter email">
                        </div>
                    </div>
                    <div class="form-row">
                        <div class="form-group col-md-6">
                            <label for="phone">Phone</label>
                            <input type="tel" class="form-control" id="phone" placeholder="Enter phone">
                        </div>
                        <div class="form-group col-md-6">
                            <label for="date">Date</label>
                            <input type="date" class="form-control" id="date">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="billAddress">Billing Address</label>
                        <textarea class="form-control" id="billAddress" rows="3" placeholder="Enter billing address"></textarea>
                    </div>
                    <div class="form-group">
                        <label for="shipAddress">Shipping Address</label>
                        <textarea class="form-control" id="shipAddress" rows="3" placeholder="Enter shipping address"></textarea>
                    </div>
                    <div class="form-group">
                        <label for="status">Status</label>
                        <select class="form-control" id="status">
                            <option value="In Transit">Created</option>
                            <option value="Delivered">InTransit</option>
                            <option value="Created">Cancelled</option>
                            <option value="Cancelled">Delievered</option>
                        </select>
                    </div>
                    <button type="submit" class="btn btn-primary btn-block">Submit</button>
                </form>
            </div>
        </div>
    </div>

</body>
</html>
